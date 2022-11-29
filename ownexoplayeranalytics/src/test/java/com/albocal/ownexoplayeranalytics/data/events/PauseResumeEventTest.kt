package com.albocal.ownexoplayeranalytics.data.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.repository.FakeAnalyticsRepository
import com.albocal.ownexoplayeranalytics.FakeData
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PauseResumeEventTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private val testDispatcherInternal = TestCoroutineDispatcher()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAnalyticsRepository
    private lateinit var fakeRepository: FakeAnalyticsRepository

    private lateinit var event: PauseResumeEvent
    private lateinit var fakeEvent: PauseResumeEvent

    @Before
    fun setUp() {
        event = PauseResumeEvent(repository, testScope)
        fakeRepository = FakeAnalyticsRepository()
        fakeEvent = PauseResumeEvent(fakeRepository, TestScope(testDispatcherInternal))
    }

    @Test
    fun  `First Frame Event has correct StateId`() {
        Truth.assertThat(event.eventId)
            .isEqualTo(AnalyticsListener.EVENT_IS_PLAYING_CHANGED)
    }

    @Test
    fun  `Pause Resume Event call on Success Test `() = testScope.runTest  {
        Truth.assertThat(fakeRepository.lastOnPauseResumeData)
            .isEqualTo(null)

        val data = 8L
        fakeEvent.onSuccess(FakeData.generateFakeEventTime(data))


        Truth.assertThat(fakeRepository.lastOnPauseResumeData)
            .isEqualTo(data)
    }
}