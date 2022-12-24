package com.arysugiarto.attendence.base

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.arysugiarto.attendence.R
import com.arysugiarto.attendence.util.Const.Paging.PER_PAGE_SMALL
import com.arysugiarto.attendence.util.color
import com.arysugiarto.attendence.util.convertDpToPixel
import kotlin.math.roundToInt

class BaseViewAdapter<T, V : View>(
        private var register: Register<T, V>,
        private var diff: Diff<T>,
        private var params: Params = Params(),
        viewClass: Class<V>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewConstructor = viewClass.getConstructor(Context::class.java)

    var items: List<T>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)

    var viewType: BaseViewType = BaseViewType.LOADING
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val diffCallBack = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
                diff.areItemsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T) =
                diff.areContentsTheSame(oldItem, newItem)
    }

    private val diffUtil = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        val width = ((params.widthPercent / 100) * parent.width).toInt()
        val height = ((params.heightPercent / 100) * parent.width).toInt()

        val baseParams = RecyclerView.LayoutParams(
                if (params.widthPercent != 0.0) width else ViewGroup.LayoutParams.WRAP_CONTENT,
                if (params.heightPercent != 0.0) height else ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(
                    context.convertDpToPixel(params.margin.left.toFloat()).roundToInt(),
                    context.convertDpToPixel(params.margin.top.toFloat()).roundToInt(),
                    context.convertDpToPixel(params.margin.right.toFloat()).roundToInt(),
                    context.convertDpToPixel(params.margin.bottom.toFloat()).roundToInt()
            )
        }

        return when(itemViewType) {
            BaseViewType.LOADING.ordinal -> {
                val shimmerView = View(context).apply {
                    val gradientDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setColor(context.color(R.color.silver))

                        cornerRadius = when(register.cornerType) {
                            is CornerType.NORMAL -> 0f
                            is CornerType.CIRCLE -> context.convertDpToPixel(200f)
                            is CornerType.ROUNDED -> (register.cornerType as CornerType.ROUNDED).radius
                        }
                    }

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    background = gradientDrawable
                }

                // Todo : Fix Params
                val shimmerViewGroup = ShimmerFrameLayout(context)
                    .apply {
                        if (params.widthPercent != 0.0 || params.heightPercent != 0.0) {
                            layoutParams = baseParams

                        }

                        addView(shimmerView)
                    }

                BaseLoadingViewHolder(shimmerViewGroup)
            }
            BaseViewType.INITIAL.ordinal -> {
                val itemView = viewConstructor
                    .newInstance(context)
                    .apply {
                        if (params.widthPercent != 0.0 || params.heightPercent != 0.0) {
                            layoutParams = baseParams
                        }
                    }

                BaseViewHolder(itemView)
            }
            else -> throw IllegalStateException("Unknown RecyclerView ViewHolder For ViewType : $itemViewType")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            BaseViewType.LOADING.ordinal -> {
                holder as BaseViewAdapter<T, V>.BaseLoadingViewHolder
            }
            BaseViewType.INITIAL.ordinal -> {
                holder as BaseViewAdapter<T, V>.BaseViewHolder
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount() = if (viewType == BaseViewType.INITIAL) items.size else register.loadingSize

    override fun getItemViewType(position: Int) = viewType.ordinal

    inner class BaseViewHolder(itemView: V) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            @Suppress("UNCHECKED_CAST")
            register.onBindHolder(bindingAdapterPosition, item, itemView as V)

            itemView.setOnClickListener {
                onItemClickCallback.invoke(itemView, item)
            }
        }
    }

    inner class BaseLoadingViewHolder(itemView: ShimmerFrameLayout): RecyclerView.ViewHolder(itemView) {
        init { itemView.startShimmer() }
    }

    private var onItemClickCallback: (view: V, item: T) -> Unit = { _, _ -> }

    fun setOnItemClickListener(callback: (view: V, item: T) -> Unit) {
        onItemClickCallback = callback
    }

    data class Register<T, V: View>(
        var onBindHolder: (position: Int, item: T, view: V) -> Unit,
        var hasLoading: Boolean = false,
        var cornerType: CornerType = CornerType.NORMAL,
        var loadingSize: Int = PER_PAGE_SMALL,
        var asyncLayout: Boolean = false
    )

    sealed class CornerType {
        object NORMAL : CornerType()
        object CIRCLE : CornerType()
        data class ROUNDED(val radius: Float): CornerType()
    }

    data class Diff<T>(
            var areContentsTheSame: (old: T, new: T) -> Boolean,
            var areItemsTheSame: (old: T, new: T) -> Boolean
    )

    data class Params(
            var widthPercent: Double = 0.0,
            var heightPercent: Double = 0.0,
            var margin: Margin = Margin()
    ) {
        data class Margin(
                val top: Int = 0,
                val left: Int = 0,
                val right: Int = 0,
                val bottom: Int = 0
        )
    }

    companion object {
        inline fun <T, reified V : View> adapterOf(
                register: Register<T, V>,
                diff: Diff<T>,
                itemList: List<T> = emptyList(),
                params: Params = Params()
        ): BaseViewAdapter<T, V> {
            return BaseViewAdapter(register, diff, params, V::class.java).apply {
                items = itemList
            }
        }

        inline fun <T, reified V: View> adapterWithShimmer(
            register: Register<T, V>,
            diff: Diff<T>,
            itemList: List<T> = emptyList(),
            params: Params = Params()
        ) = BaseViewAdapter(register, diff, params, V::class.java).apply {
            items = itemList
            viewType = if (register.hasLoading) BaseViewType.LOADING else BaseViewType.INITIAL
        }

        inline fun <reified V: View> shimmerAdapter(
                register: Register<Int, V>,
                size: Int,
                params: Params = Params()
        ): BaseViewAdapter<Int, V> {
            val diff = Diff<Int>(
                areContentsTheSame = { new, old -> new == old },
                areItemsTheSame = { new, old -> new == old }
            )

            val list = MutableList(size) {it}.toList()

            return BaseViewAdapter(register, diff, params, V::class.java).apply {
                items = list
            }
        }
    }
}