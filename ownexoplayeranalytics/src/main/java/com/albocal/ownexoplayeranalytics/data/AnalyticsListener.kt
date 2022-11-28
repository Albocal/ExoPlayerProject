package com.albocal.ownexoplayeranalytics.data

import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.albocal.ownexoplayeranalytics.data.events.FirstLoadEvent
import com.albocal.ownexoplayeranalytics.domain.IAnalyticsListener
import com.albocal.ownexoplayeranalytics.domain.IOnEventSuccess
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsListener

class AnalyticsListener(private val analyticsApplication: AnalyticsApplication, override val listeningEvents: List<IOnEventSuccess> ) : AnalyticsListener, IAnalyticsListener {

    override fun onEvents(player: Player, events: AnalyticsListener.Events){
        listeningEvents.forEach {  onEvent ->
                if(events.contains(onEvent.eventId))
                    onEvent.onSuccess(events.getEventTime(onEvent.eventId))

        }
    }
}