package com.albocal.ownexoplayeranalytics.domain

import com.google.android.exoplayer2.analytics.AnalyticsListener

interface IOnEventSuccess {
    val eventId: Int

    fun onSuccess(eventTime: AnalyticsListener.EventTime)

}