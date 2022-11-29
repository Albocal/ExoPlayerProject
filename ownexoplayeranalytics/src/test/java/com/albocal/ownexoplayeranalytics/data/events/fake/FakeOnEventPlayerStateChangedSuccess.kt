package com.albocal.ownexoplayeranalytics.data.events.fake

import com.albocal.ownexoplayeranalytics.domain.events.IOnEventPlayerStateChangedSuccess

class FakeOnEventPlayerStateChangedSuccess(override val eventId: Int) :
    IOnEventPlayerStateChangedSuccess {
    var count = 0

    override fun onSuccess() {
        count++
    }
}