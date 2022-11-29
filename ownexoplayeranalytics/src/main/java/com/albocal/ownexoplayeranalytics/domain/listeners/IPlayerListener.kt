package com.albocal.ownexoplayeranalytics.domain.listeners

import com.albocal.ownexoplayeranalytics.domain.events.IOnEventPlayerStateChangedSuccess

interface IPlayerListener {
    val listeningEventsStateChanged : List<IOnEventPlayerStateChangedSuccess>

    fun onPlaybackStateChanged(playbackState: Int)
}