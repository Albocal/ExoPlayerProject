package com.albocal.ownexoplayeranalytics.domain.events

interface IOnEventPlayerStateChangedSuccess: IOnEventPlayerSuccess {
    val eventId: Int

    fun onSuccess()

}