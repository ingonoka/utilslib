package com.ingonoka.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.offsetIn

/**
 * This code was generated like this:
 *
 * ```kotlin
 * val formatPattern = "yyyy-MM-dd'T'HH:mm:ssxxx"
 * val dateTimeFormat = DateTimeComponents.Format {
 *      byUnicodePattern(formatPattern)
 * }
 * println(DateTimeFormat.formatAsKotlinBuilderDsl(dateTimeFormat))
 * ```
 */
private val dateFormat = DateTimeComponents.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char('T')
    hour()
    char(':')
    minute()
    char(':')
    second()
    offsetHours(Padding.ZERO)
    char(':')
    offsetMinutesOfHour(Padding.ZERO)
}

/**
 * Format an instant as String in [timeZone].
 *
 * The function ensures that the formatted string will always have the form `yyyy-MM-dd'T'HH:mm:ssxxx`, which means
 *
 * * if [timeZone] is UTC, then `+00:00` will be used and NOT `Z`,
 * * the year is always 4 digits padded with zero if necessary
 * * Only full seconds are considered (no rounding up!)
 * * month will always be two digits padded with a zero if necessary
 * * if [timeZone] was created with an explicit number of offset hours such as "+08:00", then summer time is not
 * considered. If created with a known timezone name such as "Europe/London", then summer time will be considered
 */
fun Instant.formatTime(timeZone: TimeZone): String = format(dateFormat, offsetIn(timeZone))

/**
 * Scan String and convert to [Instant]. This ensures the canonical format as described in [Instant.formatTime]
 *
 * The string must be formatted the same way it would be printed with [Instant.formatTime]
 */
fun String.scanTime(): Instant = Instant.parse(this , dateFormat)