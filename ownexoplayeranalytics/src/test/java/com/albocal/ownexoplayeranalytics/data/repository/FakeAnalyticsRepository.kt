package com.albocal.ownexoplayeranalytics.data.repository

import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeAnalyticsRepository : IAnalyticsRepository {
    override val pauseStateFlow: StateFlow<EventCountModel> = MutableStateFlow(EventCountModel(0,0L))
    override val resumeStateFlow: StateFlow<EventCountModel> = MutableStateFlow(EventCountModel(0,0L))

    var lastOnPauseResumeData : Long? = null
    var lastOnLoadStartsData : Long? = null
    var lastOnRenderFirstFrameData : Long? = null
    var onFinishVideoCount : Int = 0

    override suspend fun onPauseResume(timestamp: Long) {
        lastOnPauseResumeData = timestamp
    }

    override suspend fun onLoadStarts(timestamp: Long) {
        lastOnLoadStartsData = timestamp
    }

    override suspend fun onRenderFirstFrame(timestamp: Long) {
        lastOnRenderFirstFrameData = timestamp
    }

    override suspend fun onFinishVideo() {
        onFinishVideoCount++
    }
}