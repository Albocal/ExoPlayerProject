package com.albocal.ownexoplayeranalytics.domain.listeners

import com.albocal.ownexoplayeranalytics.domain.events.IOnAnalyticsEventSuccess
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsListener

interface IAnalyticsListener {
    val listeningEvents : List<IOnAnalyticsEventSuccess>

    fun onEvents(player: Player, events: AnalyticsListener.Events)
}