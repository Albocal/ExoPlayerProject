package com.albocal.ownexoplayeranalytics.data.events.fake

import com.albocal.ownexoplayeranalytics.domain.events.IOnAnalyticsEventSuccess
import com.google.android.exoplayer2.analytics.AnalyticsListener

class FakeOnAnalyticsEventSuccess(override val eventId: Int) : IOnAnalyticsEventSuccess {
    var lastEventTime : AnalyticsListener.EventTime? = null

    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        lastEventTime = eventTime
    }
}