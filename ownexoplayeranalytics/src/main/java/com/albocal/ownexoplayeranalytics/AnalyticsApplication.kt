package com.albocal.ownexoplayeranalytics

import android.content.Context
import com.albocal.ownexoplayeranalytics.data.listeners.AnalyticsListener
import com.albocal.ownexoplayeranalytics.data.listeners.PlayerListener
import com.albocal.ownexoplayeranalytics.data.events.*
import com.albocal.ownexoplayeranalytics.data.remote.service.AnalyticsService
import com.albocal.ownexoplayeranalytics.data.repository.AnalyticsRepositoryImpl
import com.albocal.ownexoplayeranalytics.domain.repository.IAnalyticsRepository
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.CoroutineContext

class AnalyticsApplication(private val context: Context, private val coroutineContext: CoroutineContext) {

    /** Injections **/
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


    /** Listeners **/

    val analyticsListener = AnalyticsListener(this,
        listOf(
            FirstLoadEvent(repository, coroutineScope),
            FirstFrameEvent(repository, coroutineScope),
            PauseResumeEvent(repository, coroutineScope))
        )

    val playerListener = PlayerListener(this,
        listOf(
            EndEvent(repository, coroutineScope)
        ))


    /** Flows **/

    val pauseFlow = repository.pauseStateFlow

    val resumeFlow = repository.resumeStateFlow
}