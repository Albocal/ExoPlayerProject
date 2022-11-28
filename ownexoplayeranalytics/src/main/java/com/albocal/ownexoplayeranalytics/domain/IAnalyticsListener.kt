package com.albocal.ownexoplayeranalytics.domain

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.AnalyticsListener

interface IAnalyticsListener {
    val listeningEvents : List<IOnEventSuccess>

    fun onEvents(player: Player, events: AnalyticsListener.Events)
}