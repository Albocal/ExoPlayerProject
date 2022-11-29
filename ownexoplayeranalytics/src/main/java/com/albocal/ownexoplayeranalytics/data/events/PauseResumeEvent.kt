package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.events.IOnAnalyticsEventSuccess
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_IS_PLAYING_CHANGED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PauseResumeEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) :
    IOnAnalyticsEventSuccess {
    override val eventId: Int = EVENT_IS_PLAYING_CHANGED

    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        scope.launch{
            analyticsRepository.onPauseResume(eventTime.realtimeMs)
        }
    }
}