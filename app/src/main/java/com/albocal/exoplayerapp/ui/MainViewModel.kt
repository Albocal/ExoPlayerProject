package com.albocal.exoplayerapp.ui

import com.albocal.exoplayerapp.base.BaseViewModel
import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val analitics : AnalyticsApplication
) : BaseViewModel() {

    val pauseState = analitics.pauseTimes

    val resumeState = analitics.resumeTimes

}