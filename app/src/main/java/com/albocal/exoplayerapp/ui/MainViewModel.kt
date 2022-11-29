package com.albocal.exoplayerapp.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.albocal.exoplayerapp.base.BaseViewModel
import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Math.abs
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val analytics : AnalyticsApplication
) : BaseViewModel() {

    private val pauseState = analytics.pauseFlow
    private val resumeState = analytics.resumeFlow
    private var elapsed = 0

    private val _pauseText = MutableStateFlow(pauseText())
    val pauseTextFlow: StateFlow<String> = _pauseText

    private val _resumeText = MutableStateFlow(resumeText())
    val resumeTextFlow: StateFlow<String> = _resumeText

    private val _elapsedText = MutableStateFlow(elapsedText())
    val elapsedTextFlow: StateFlow<String> = _elapsedText

    init {
        subscribePauseState()
        subscribeResumeState()
    }

    private fun subscribePauseState() {
        viewModelScope.launch {
            pauseState.collect { value ->
                refreshElapsedTime()
                emitPause()
                emitElapsedTime()
            }
        }
    }

    private fun subscribeResumeState() {
        viewModelScope.launch {
            resumeState.collect { value ->
                refreshElapsedTime()
                emitResume()
                emitElapsedTime()
            }
        }
    }

    private fun refreshElapsedTime() {
        elapsed = if(pauseState.value.timestampLastEvent == 0L ||  resumeState.value.timestampLastEvent == 0L) 0 else kotlin.math.abs(resumeState.value.timestampLastEvent - pauseState.value.timestampLastEvent).toInt()
    }

    private fun emitPause() {
        _pauseText.tryEmit(pauseText())
    }

    private fun emitResume() {
        _resumeText.tryEmit(resumeText())
    }
    private fun emitElapsedTime() {

        _elapsedText.tryEmit(elapsedText())
    }

    private fun pauseText() = "Pause: ${pauseState.value.qty}"

    private fun resumeText() = "Resume: ${resumeState.value.qty}"

    private fun elapsedText() = "Elapsed time[MS]: $elapsed"
}