package com.albocal.ownexoplayeranalytics.data.events

import com.albocal.ownexoplayeranalytics.domain.events.IOnEventPlayerStateChangedSuccess
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EndEvent(private val analyticsRepository: IAnalyticsRepository, private val scope: CoroutineScope) :
    IOnEventPlayerStateChangedSuccess {

    override val eventId: Int = Player.STATE_ENDED

    override fun onSuccess() {
        scope.launch{
            analyticsRepository.onFinishVideo()
        }

    }

}