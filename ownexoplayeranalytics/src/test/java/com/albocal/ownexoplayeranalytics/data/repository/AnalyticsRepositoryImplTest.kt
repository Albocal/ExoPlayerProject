package com.albocal.ownexoplayeranalytics.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.remote.FakeAnalyticsService
import com.albocal.ownexoplayeranalytics.data.remote.entity.EventStampRequest
import com.albocal.ownexoplayeranalytics.data.remote.entity.NO_TIMESTAMP
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FINISHED
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_FRAME
import com.albocal.ownexoplayeranalytics.domain.model.EVENT_ID_FIRST_LOAD
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AnalyticsRepositoryImplTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: FakeAnalyticsService

    private lateinit var repository: AnalyticsRepositoryImpl

    @Before
    fun setUp() {
        service = FakeAnalyticsService()
        repository = AnalyticsRepositoryImpl(service)
    }

    @Test
    fun  `onPauseResume no call Test `() = testScope.runTest {
        val zeroItemResume = repository.resumeStateFlow.first()

        Truth.assertThat(zeroItemResume)
            .isEqualTo(EventCountModel(0, 0L))

        val zeroItemPause = repository.pauseStateFlow.first()

        Truth.assertThat(zeroItemPause)
            .isEqualTo(EventCountModel(0, 0L))
    }

    @Test
    fun  `onPauseResume first call (resume) Test `() = testScope.runTest {
        val firstValue = 10L

        repository.onPauseResume(firstValue)

        val firstItemResume = repository.resumeStateFlow.first()

        Truth.assertThat(firstItemResume)
            .isEqualTo(EventCountModel(1, 10L))

        val zeroItemPause = repository.pauseStateFlow.first()

        Truth.assertThat(zeroItemPause)
            .isEqualTo(EventCountModel(0, 0L))
    }

    @Test
    fun  `onPauseResume second call (pause) Test `() = testScope.runTest {
        val resumeData = 10L
        val pauseData = 99L
        repository.onPauseResume(resumeData)
        repository.onPauseResume(pauseData)
        val itemResume = repository.resumeStateFlow.first()

        Truth.assertThat(itemResume)
            .isEqualTo(EventCountModel(1, resumeData))

        val itemPause = repository.pauseStateFlow.first()

        Truth.assertThat(itemPause)
            .isEqualTo(EventCountModel(1, pauseData))
    }

    @Test
    fun  `onLoadStarts first call Test `() = testScope.runTest {
        val data = 10L

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(0)

        Truth.assertThat(repository.firstLoadWasEmitted)
            .isEqualTo(false)

        repository.onLoadStarts(data)

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(1)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FIRST_LOAD, data))
        Truth.assertThat(repository.firstLoadWasEmitted)
            .isEqualTo(true)
    }

    @Test
    fun  `onLoadStarts x calls Test `() = testScope.runTest {
        val firstData = 10L
        val wrongData = 99L
        repository.onLoadStarts(firstData)

        repository.onLoadStarts(wrongData)
        repository.onLoadStarts(wrongData)
        repository.onLoadStarts(wrongData)

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(1)

        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FIRST_LOAD, firstData))

        repository.onRenderFirstFrame(wrongData)
        repository.onFinishVideo()

        repository.onLoadStarts(wrongData)
        repository.onLoadStarts(wrongData)
        repository.onLoadStarts(wrongData)
        repository.onLoadStarts(wrongData)

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(3)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FINISHED, NO_TIMESTAMP))
    }

    @Test
    fun  `onRenderFirstFrame first time Test `() = testScope.runTest {
        val firstData = 10L
        val wrongData = 99L

        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)

        repository.onRenderFirstFrame(firstData)


        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(true)

        repository.onRenderFirstFrame(wrongData)
        repository.onRenderFirstFrame(wrongData)
        repository.onRenderFirstFrame(wrongData)
        repository.onRenderFirstFrame(wrongData)

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(1)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FIRST_FRAME, firstData))
    }

    @Test
    fun  `onRenderFirstFrame x times no restart Test `() = testScope.runTest {
        val firstData = 10L
        val wrongData = 99L
        val moreData = 78L
        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)
        repository.onRenderFirstFrame(firstData)

        repository.onRenderFirstFrame(wrongData)

        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(true)
        Truth.assertThat(service.qtyCalls)
            .isEqualTo(1)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FIRST_FRAME, firstData))

        repository.onFinishVideo()

        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)

        repository.onRenderFirstFrame(moreData)

        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(true)
        Truth.assertThat(service.qtyCalls)
            .isEqualTo(3)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FIRST_FRAME, moreData))
    }

    @Test
    fun  `onFinishVideo 1 Test `() = testScope.runTest {
        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)
        repository.firstRenderDuringVideoWasEmitted = true

        repository.onFinishVideo()

        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)
    }

    @Test
    fun  `onFinishVideo 2 Test `() = testScope.runTest {
        Truth.assertThat(repository.firstRenderDuringVideoWasEmitted)
            .isEqualTo(false)
        repository.firstRenderDuringVideoWasEmitted = true

        repository.onFinishVideo()

        Truth.assertThat(service.qtyCalls)
            .isEqualTo(1)
        Truth.assertThat(service.lastRequest)
            .isEqualTo(EventStampRequest(EVENT_ID_FINISHED, NO_TIMESTAMP))

    }
}