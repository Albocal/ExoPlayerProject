package com.albocal.ownexoplayeranalytics

import android.content.Context
import com.albocal.ownexoplayeranalytics.data.AnalyticsListener
import com.albocal.ownexoplayeranalytics.data.events.*
import com.albocal.ownexoplayeranalytics.data.remote.service.AnalyticsService
import com.albocal.ownexoplayeranalytics.data.repository.AnalyticsRepositoryImpl
import com.albocal.ownexoplayeranalytics.domain.model.EventCountModel
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.CoroutineContext

class AnalyticsApplication(context: Context, coroutineContext: CoroutineContext) {

    private val _pauseTimes = MutableStateFlow(EventCountModel(0,0))
    val pauseTimes: StateFlow<EventCountModel> = _pauseTimes

    private val _resumeTimes = MutableStateFlow(EventCountModel(0,0))
    val resumeTimes: StateFlow<EventCountModel> = _resumeTimes

    private val service : AnalyticsService  = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .build()
            .create(AnalyticsService::class.java)

    private val repository : IAnalyticsRepository = AnalyticsRepositoryImpl(service)

    private val coroutineScope = CoroutineScope(coroutineContext)

    val listener = AnalyticsListener(this,
        listOf(
            FirstLoadEvent(repository, coroutineScope),
            FirstFrameEvent(repository, coroutineScope),
            PauseEvent(_pauseTimes),
            ResumeEvent(_resumeTimes),
        ))

}