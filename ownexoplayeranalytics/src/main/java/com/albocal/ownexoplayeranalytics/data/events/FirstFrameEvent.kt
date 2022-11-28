package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_RENDERED_FIRST_FRAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FirstFrameEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) : IOnEventSuccess {
    override val eventId: Int = EVENT_RENDERED_FIRST_FRAME

    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        scope.launch{
            analyticsRepository.onFirstFrame(eventTime.realtimeMs)
        }
    }
}