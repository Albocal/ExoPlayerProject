package com.albocal.ownexoplayeranalytics.data.remote

import com.albocal.ownexoplayeranalytics.data.remote.entity.EventStampRequest
import com.albocal.ownexoplayeranalytics.data.remote.service.AnalyticsService

class FakeAnalyticsService : AnalyticsService {
    var qtyCalls = 0
    var lastRequest : EventStampRequest? = null

    override suspend fun sendNewEvent(request: EventStampRequest) {
        qtyCalls++
        lastRequest = request
    }
}