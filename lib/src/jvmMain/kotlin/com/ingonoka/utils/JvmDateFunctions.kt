/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQuery

/**
 * Convert a [Long] representing the seconds since the unix epoch Jan 1, 00:00:00.000 into a [String] with the
 * format "1970-01-01T00:00:00+00:00".  The function always uses "UTC" as time zone.
 */
actual fun Long.epochSecondsToString(): String = DateTimeFormatter
    .ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx")
    .withZone(ZoneId.of("UTC"))
    .format(Instant.ofEpochSecond(this))

actual fun String.asUtcToEpochSeconds(): Long= Instant.from(DateTimeFormatter
    .ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx")
    .withZone(ZoneId.of("UTC"))
    .parse(this)).epochSecond
