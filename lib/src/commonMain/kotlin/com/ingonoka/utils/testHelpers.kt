/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

internal fun <T> join(theArray: Array<T>?): String =
    if (theArray == null) "null" else "[${theArray.joinToString(", ")}]"

internal fun <T> assertArrayEquals(a1: Array<T>?, a2: Array<T>?) {
    if (!arraysEqual(a1, a2)) {
        failFirstSecond("Expected Arrays to be equal, but were different", join(a1), join(a2))
    }
}

internal fun <T> assertArrayNotEquals(a1: Array<T>?, a2: Array<T>?) {
    if (arraysEqual(a1, a2)) {
        failFirstSecond("Expected Arrays to be different, but were equal", join(a1), join(a2))
    }
}

internal fun <T> arraysEqual(a1: Array<T>?, a2: Array<T>?): Boolean {
    if (a1 == null || a2 == null) return false
    return a1 contentEquals a2
}

internal fun failFirstSecond(message: String, first: String?, second: String?): Nothing = throw Exception(
    """
    |$message
    |   First:      $first
    |   Second:     $second
""".trimMargin()
)