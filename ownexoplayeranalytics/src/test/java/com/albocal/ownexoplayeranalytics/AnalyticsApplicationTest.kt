package com.albocal.ownexoplayeranalytics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albocal.ownexoplayeranalytics.data.repository.FakeAnalyticsRepository
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import com.google.android.exoplayer2.Player
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
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
class AnalyticsApplicationTest {

    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private val testDispatcherInternal = TestCoroutineDispatcher()

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var library: AnalyticsApplication

    @Before
    fun setUp() {
        library = AnalyticsApplication(testDispatcherInternal)
    }


    @Test
    fun  `End2End examples Test`() = testScope.runTest  {
        val zeroItemResume = library.resumeFlow.first()

        Truth.assertThat(zeroItemResume)
            .isEqualTo(EventCountModel(0, 0L))

        val zeroItemPause = library.resumeFlow.first()

        Truth.assertThat(zeroItemPause)
            .isEqualTo(EventCountModel(0, 0L))

    }
}