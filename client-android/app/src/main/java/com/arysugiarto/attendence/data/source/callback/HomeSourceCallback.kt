package com.arysugiarto.attendence.data.source.callback

import com.arysugiarto.attendence.data.remote.model.SurveySend
import kotlinx.coroutines.flow.Flow


interface HomeSourceCallback {
    fun submitAbsen(surveySend: SurveySend): Flow<com.arysugiarto.attendence.data.remote.Result<SurveySend>>
}