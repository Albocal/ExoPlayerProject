package com.albocal.ownexoplayeranalytics.domain.repository

interface IAnalyticsRepository {
    suspend fun onFirstLoad(timestamp: Long)

    suspend fun onFirstFrame(timestamp: Long)

    suspend fun onFinishVideo(timestamp: Long)
}