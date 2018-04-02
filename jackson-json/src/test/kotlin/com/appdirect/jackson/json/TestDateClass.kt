package com.appdirect.jackson.json

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime

data class TestDateClass (val instant: Instant = Instant.now(),
                          val zonedDateTime: ZonedDateTime = ZonedDateTime.now(),
                          val localDateTime: LocalDateTime = LocalDateTime.now(),
                          val offsetDateTime: OffsetDateTime = OffsetDateTime.now())