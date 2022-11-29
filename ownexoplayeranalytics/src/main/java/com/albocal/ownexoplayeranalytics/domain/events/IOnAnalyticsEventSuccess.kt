package com.albocal.ownexoplayeranalytics.domain.events

import com.google.android.exoplayer2.analytics.AnalyticsListener

interface IOnAnalyticsEventSuccess: IOnEventSuccess {
    val eventId: Int

    fun onSuccess(eventTime: AnalyticsListener.EventTime)

}