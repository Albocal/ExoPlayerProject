package com.albocal.ownexoplayeranalytics.domain.repository

import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import kotlinx.coroutines.flow.StateFlow

interface IAnalyticsRepository {

    val pauseStateFlow: StateFlow<EventCountModel>

    val resumeStateFlow: StateFlow<EventCountModel>

    suspend fun onPauseResume(timestamp: Long)

    suspend fun onLoadStarts(timestamp: Long)

    suspend fun onRenderFirstFrame(timestamp: Long)

    suspend fun onFinishVideo()
}