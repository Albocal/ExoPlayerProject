package com.albocal.ownexoplayeranalytics.data.repository

import com.albocal.ownexoplayeranalytics.data.remote.entity.EventStampRequest
import com.albocal.ownexoplayeranalytics.data.remote.entity.NO_TIMESTAMP
import com.albocal.ownexoplayeranalytics.data.remote.service.AnalyticsService
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FINISHED
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_FRAME
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_LOAD
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnalyticsRepositoryImpl(
    private val analyticsService: AnalyticsService,
): IAnalyticsRepository {

    private var pauseResumeCount = 0

    private val _pauseTimes = MutableStateFlow(EventCountModel(0,0L))
    override val pauseStateFlow: StateFlow<EventCountModel> = _pauseTimes

    private val _resumeTimes = MutableStateFlow(EventCountModel(0,0L))
    override val resumeStateFlow: StateFlow<EventCountModel> = _resumeTimes

    private var firstLoadWasEmitted = false

    private var firstRenderDuringVideoWasEmitted = false

    override suspend fun onPauseResume(timestamp: Long) {
        pauseResumeCount++

        if (pauseResumeCount % 2 == 0)
            _pauseTimes.emit(EventCountModel(_pauseTimes.value.qty+1, timestamp))
        else
            _resumeTimes.emit(EventCountModel(_resumeTimes.value.qty+1, timestamp))
    }

    override suspend fun onLoadStarts(timestamp: Long) {
        if(firstLoadWasEmitted)
            return
        firstLoadWasEmitted = true
        try {
            analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FIRST_LOAD, timestamp))
        } catch (err: Exception){
            err.printStackTrace()
        }

    }

    override suspend fun onRenderFirstFrame(timestamp: Long) {
        if(firstRenderDuringVideoWasEmitted)
            return
        firstRenderDuringVideoWasEmitted = true
        try {
            analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FIRST_FRAME, timestamp))
        } catch (err: Exception){
            err.printStackTrace()
        }
    }

    override suspend fun onFinishVideo() {
        firstRenderDuringVideoWasEmitted = false
        try {
            analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FINISHED, NO_TIMESTAMP))
        } catch (err: Exception){
            err.printStackTrace()
        }
    }
}