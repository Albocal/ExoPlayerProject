package com.albocal.ownexoplayeranalytics

import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.analytics.AnalyticsListener

object FakeData {
    class FakeTimeLine : Timeline() {
        override fun getWindowCount(): Int {
            return 1
        }

        override fun getWindow(
            windowIndex: Int,
            window: Window,
            defaultPositionProjectionUs: Long
        ): Window {
            return window
        }

        override fun getPeriodCount(): Int {
            return 1
        }

        override fun getPeriod(periodIndex: Int, period: Period, setIds: Boolean): Period {
            return period
        }

        override fun getIndexOfPeriod(uid: Any): Int {
            return 1
        }

        override fun getUidOfPeriod(periodIndex: Int): Any {
            return 1
        }

    }

    fun generateFakeEventTime(realMS: Long) = AnalyticsListener.EventTime(
        realMS,
        FakeTimeLine(),
        0,
        null,
        0L,
        FakeTimeLine(),
        0,
        null,
        0L,
        0L

    )
}