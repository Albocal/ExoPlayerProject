package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import kotlinx.coroutines.CoroutineScope

class EndEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) : IOnEventSuccess {
    override val eventId: Int = -1
    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        //TODO I have not found any documentation related with end events yet
    }
}