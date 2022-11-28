package com.albocal.ownexoplayeranalytics.data.repository

import com.albocal.ownexoplayeranalytics.data.remote.entity.EventStampRequest
import com.albocal.ownexoplayeranalytics.data.remote.service.AnalyticsService
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FINISHED
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_FRAME
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_LOAD
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository

class AnalyticsRepositoryImpl(private val analyticsService: AnalyticsService): IAnalyticsRepository {
    override suspend fun onFirstLoad(timestamp: Long) {
        analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FIRST_LOAD, timestamp))
    }

    override suspend fun onFirstFrame(timestamp: Long) {
        analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FIRST_FRAME, timestamp))
    }

    override suspend fun onFinishVideo(timestamp: Long) {
        analyticsService.sendNewEvent(EventStampRequest(EVENT_ID_FINISHED, timestamp))
    }
}