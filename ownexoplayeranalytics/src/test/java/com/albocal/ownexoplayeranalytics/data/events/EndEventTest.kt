package com.albocal.ownexoplayeranalytics.data.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.repository.FakeAnalyticsRepository
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.Player
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
class EndEventTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private val testDispatcherInternal = TestCoroutineDispatcher()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IAnalyticsRepository
    private lateinit var fakeRepository: FakeAnalyticsRepository

    private lateinit var event: EndEvent
    private lateinit var fakeEvent: EndEvent

    @Before
    fun setUp() {
        event = EndEvent(repository, testScope)
        fakeRepository = FakeAnalyticsRepository()
        fakeEvent  = EndEvent(fakeRepository, TestScope(testDispatcherInternal))
    }

    @Test
    fun  `EndEvent has correct StateId Test`() {
        Truth.assertThat(event.eventId)
            .isEqualTo(Player.STATE_ENDED)
    }

    @Test
    fun  `EndEvent call on Success Test `() = testScope.runTest  {
        Truth.assertThat(fakeRepository.onFinishVideoCount)
            .isEqualTo(0)

        fakeEvent.onSuccess()

        Truth.assertThat(fakeRepository.onFinishVideoCount)
            .isEqualTo(1)

        fakeEvent.onSuccess()
        fakeEvent.onSuccess()
        fakeEvent.onSuccess()
        fakeEvent.onSuccess()


        Truth.assertThat(fakeRepository.onFinishVideoCount)
            .isEqualTo(5)
    }
}