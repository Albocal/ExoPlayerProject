package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.events.IOnAnalyticsEventSuccess
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_LOAD_COMPLETED
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_LOAD_STARTED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FirstLoadEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) :
    IOnAnalyticsEventSuccess {
    override val eventId: Int = EVENT_LOAD_STARTED

    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        scope.launch{
            analyticsRepository.onLoadStarts(eventTime.realtimeMs)
        }
    }
}