package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_IS_PLAYING_CHANGED
import com.google.android.exoplayer2.analytics.AnalyticsListener.EVENT_LOAD_STARTED
import kotlinx.coroutines.flow.MutableStateFlow

class ResumeEvent(private val _resumeTimes: MutableStateFlow<EventCountModel>) : IOnEventSuccess {
    override val eventId: Int = EVENT_IS_PLAYING_CHANGED
    private var qtyEvents = 0
    override fun onSuccess(eventTime: AnalyticsListener.EventTime) {
        qtyEvents++
        if (qtyEvents % 2 == 1)
            _resumeTimes.tryEmit(EventCountModel(_resumeTimes.value.qty+1, eventTime.realtimeMs))
    }
}