package com.albocal.ownexoplayeranalytics.data.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.repository.FakeAnalyticsRepository
import com.albocal.ownexoplayeranalytics.FakeData
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FirstFrameEventTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private val testDispatcherInternal = TestCoroutineDispatcher()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAnalyticsRepository
    private lateinit var fakeRepository: FakeAnalyticsRepository

    private lateinit var event: FirstFrameEvent
    private lateinit var fakeEvent: FirstFrameEvent

    @Before
    fun setUp() {
        event = FirstFrameEvent(repository, testScope)
        fakeRepository = FakeAnalyticsRepository()
        fakeEvent = FirstFrameEvent(fakeRepository, TestScope(testDispatcherInternal))
    }

    @Test
    fun  `First Frame Event has correct StateId Test`() {
        Truth.assertThat(event.eventId)
            .isEqualTo(AnalyticsListener.EVENT_RENDERED_FIRST_FRAME)
    }

    @Test
    fun  `First Frame Event call on Success Test `() = testScope.runTest  {
        Truth.assertThat(fakeRepository.lastOnRenderFirstFrameData)
            .isEqualTo(null)

        val data = 10L
        fakeEvent.onSuccess(FakeData.generateFakeEventTime(data))


        Truth.assertThat(fakeRepository.lastOnRenderFirstFrameData)
            .isEqualTo(data)
    }


}