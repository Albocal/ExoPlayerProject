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
class FirstLoadEventTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private val testDispatcherInternal = TestCoroutineDispatcher()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAnalyticsRepository
    private lateinit var fakeRepository: FakeAnalyticsRepository

    private lateinit var event: FirstLoadEvent
    private lateinit var fakeEvent: FirstLoadEvent


    @Before
    fun setUp() {
        event = FirstLoadEvent(repository, testScope)
        fakeRepository = FakeAnalyticsRepository()
        fakeEvent = FirstLoadEvent(fakeRepository, TestScope(testDispatcherInternal))
    }

    @Test
    fun  `First Load Event has correct StateId Test`() {
        Truth.assertThat(event.eventId)
            .isEqualTo(AnalyticsListener.EVENT_LOAD_STARTED)
    }

    @Test
    fun  `First Load Event call on Success Test `() = testScope.runTest  {
        Truth.assertThat(fakeRepository.lastOnLoadStartsData)
            .isEqualTo(null)

        val data = 99L
        fakeEvent.onSuccess(FakeData.generateFakeEventTime(data))


        Truth.assertThat(fakeRepository.lastOnLoadStartsData)
            .isEqualTo(data)
    }
}