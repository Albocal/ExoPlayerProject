package com.albocal.ownexoplayeranalytics.data.listeners

import android.util.Log
import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.albocal.ownexoplayeranalytics.domain.listeners.IPlayerListener
import com.albocal.ownexoplayeranalytics.domain.events.IOnEventPlayerStateChangedSuccess
import com.google.android.exoplayer2.Player


class PlayerListener(private val analyticsApplication: AnalyticsApplication, override val listeningEventsStateChanged: List<IOnEventPlayerStateChangedSuccess> ) : Player.Listener,
    IPlayerListener {

    override fun onPlaybackStateChanged(playbackState: Int) {
        listeningEventsStateChanged.find { it.eventId == playbackState }?.onSuccess()
    }
}