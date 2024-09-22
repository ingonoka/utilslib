/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.qcatlight.utils


/**
 * Interpret Long as seconds and convert into a Duration string
 */
fun Long.asSecondsToDurationString(): String {
    if (this == 0L) {
        return "PT0S"
    }

    val hours = this / SECONDS_PER_HOUR
    val minutes = (this % SECONDS_PER_HOUR) / 60
    val secs = this % 60

    return buildString {
        append("PT")
        if (hours != 0L) {
            append(hours).append('H')
        }
        if (minutes != 0L) {
            append(minutes).append('M')
        }
        if (secs != 0L) {
            append(secs).append('S')
        }
    }
}

val PATTERN = Regex("P(([0-9]+)D)?(T(([0-9]+)H)?(([0-9]+)M)?(([0-9]+)S)?)*", RegexOption.IGNORE_CASE)
const val DAY_INDEX = 2
const val HOUR_INDEX = 5
const val MINUTE_INDEX = 7
const val SECOND_INDEX = 9
const val SECONDS_PER_DAY = 86_400
const val SECONDS_PER_HOUR = 3_600

/**
 * Interpret a [String] as a duration description and convert into number of seconds.
 *
 *
 * - "PT15S"     -- parses as "15 Seconds"
 *
 * - "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
 *
 * - "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
 *
 * - "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
 *
 * - "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
 *
 */
fun String.asDurationToSeconds(): Long =

    PATTERN.matchEntire(this)?.let { matches ->
        (if (matches.groupValues[DAY_INDEX].isNotEmpty()) matches.groupValues[DAY_INDEX].toLong() * SECONDS_PER_DAY else 0) +
                (if (matches.groupValues[HOUR_INDEX].isNotEmpty()) matches.groupValues[HOUR_INDEX].toLong() * SECONDS_PER_HOUR else 0) +
                (if (matches.groupValues[MINUTE_INDEX].isNotEmpty()) matches.groupValues[MINUTE_INDEX].toLong() * 60 else 0) +
                (if (matches.groupValues[SECOND_INDEX].isNotEmpty()) matches.groupValues[SECOND_INDEX].toLong() else 0)
    } ?: throw Exception("Incorrect duration string: $this")


