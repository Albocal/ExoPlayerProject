package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_LOAD_COMPLETED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FirstLoadEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) : IOnEventSuccess {
    override val eventId: Int = EVENT_LOAD_COMPLETED
    private var wasEmitted = false

    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        if(wasEmitted)
            return

        wasEmitted = true
        scope.launch{
            analyticsRepository.onFirstLoad(eventTime.realtimeMs)
        }
    }
}