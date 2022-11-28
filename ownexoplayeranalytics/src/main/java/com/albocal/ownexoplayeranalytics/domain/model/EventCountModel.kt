package com.albocal.ownexoplayeranalytics.domain.model

data class EventCountModel(
    val qty: Int,
    val timestampLastEvent: Long
)

const val EVENT_ID_FIRST_LOAD = 10000
const val EVENT_ID_FIRST_FRAME = 10001
const val EVENT_ID_FINISHED = 10002