package com.arysugiarto.attendence.util.livevent

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class EventObserver<T> (private val action: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {

        event?.data?.let { item ->
            action.invoke(item)
        }
    }
}

fun <T> LiveData<Event<T>>.observe(
    lifecycleOwner: LifecycleOwner,
    observer: (T) -> Unit
) = observe(lifecycleOwner, EventObserver(observer))