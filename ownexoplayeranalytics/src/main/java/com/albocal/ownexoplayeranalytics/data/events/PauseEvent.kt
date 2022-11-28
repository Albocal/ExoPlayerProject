package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_IS_PLAYING_CHANGED
import kotlinx.coroutines.flow.MutableStateFlow

class PauseEvent(private val _pauseTimes: MutableStateFlow<EventCountModel>) : IOnEventSuccess {
    override val eventId: Int = EVENT_IS_PLAYING_CHANGED
    private var qtyEvents = 0
    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        qtyEvents++
        if(qtyEvents%2 == 0)
            _pauseTimes.tryEmit(EventCountModel(_pauseTimes.value.qty+1,eventTime.realtimeMs))
    }
}