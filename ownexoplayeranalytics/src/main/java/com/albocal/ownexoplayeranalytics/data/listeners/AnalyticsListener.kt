package com.albocal.ownexoplayeranalytics.data.listeners

import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.albocal.ownexoplayeranalytics.domain.listeners.IAnalyticsListener
import com.albocal.ownexoplayeranalytics.domain.events.IOnAnalyticsEventSuccess
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsListener

class AnalyticsListener(private val analyticsApplication: AnalyticsApplication, override val listeningEvents: List<IOnAnalyticsEventSuccess> ) : AnalyticsListener,
    IAnalyticsListener {

    override fun onEvents(player: Player, events: AnalyticsListener.Events){
        listeningEvents.forEach {  onEvent ->
                if(events.contains(onEvent.eventId))
                    onEvent.onSuccess(events.getEventTime(onEvent.eventId))

        }
    }
}