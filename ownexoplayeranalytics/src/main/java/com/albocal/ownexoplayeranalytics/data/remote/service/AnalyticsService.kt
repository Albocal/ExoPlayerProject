package com.albocal.ownexoplayeranalytics.data.remote.service

import com.albocal.ownexoplayeranalytics.data.remote.entity.EventStampRequest
import retrofit2.http.*

interface AnalyticsService {
    @POST("logs/events")
    suspend fun sendNewEvent(
        @Body request: EventStampRequest
    )
}
