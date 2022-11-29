package com.albocal.ownexoplayeranalytics.data.remote.entity

data class EventStampRequest(
    val eventId: Int,
    val timestamp: Long
)

const val NO_TIMESTAMP = -1L