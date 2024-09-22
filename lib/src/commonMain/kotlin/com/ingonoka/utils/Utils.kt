/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */

package com.ingonoka.utils

/**
 * Convert [String] into a [ByteArray] of UTF8 characters.
 */
expect fun String.toByteArray(): ByteArray

/**
 * Convert [String] into a [CharArray]
 */
expect fun String.toCharacterArray(): CharArray

/**
 * Compare two lists.
 *
 * - Lists are equal if both are null
 * - Lists are equal if they both have the same number of elements and each element of list 1 is contained in list 2
 * regardless of position
 */
fun <T> compareLists(a: List<T>?, b: List<T>?): Boolean {
    if (a == null && b == null) return true

    if (a == null || b == null) return false

    val aSet = a.toSet()
    val bSet = b.toSet()

    if (aSet.size != bSet.size) return false

    return aSet.all { t: T -> bSet.contains(t) }

}

/**
 * Helper function to build byte array using a [PoorMansByteBuffer].
 *
 * Usage:
 * ```
 * buildByteArray {
 *  writeInt(1)
 *  writeByte(0x1)
 * }.materialize { throw it }
 * ```
 */
fun buildByteArray(builder: PoorMansByteBuffer.() -> Unit): SingleResult<ByteArray> =
    try {
        SingleResult.success(
            PoorMansByteBuffer().apply {
                builder()
            }.toByteArray()
        )
    } catch (e: Exception) {
        SingleResult.failure(Exception("Building byte array failed", e))
    }

/**
 * Collect the messages from a stack trace of a [Throwable] into a list of strings
 */
fun Throwable.collectExceptionMessages(): List<String> =

    with(mutableListOf<String>()) {
        var exception: Throwable? = this@collectExceptionMessages
        do {
            exception?.message?.let { add(it) } ?: add("No message")
            exception = exception?.cause
        } while (exception != null)
        toList()
    }

/**
 * Concatenate messages from the call stack of a [Throwable] separated by [separator] and return as string
 */
fun Throwable.combineAllMessages(separator: String): String = collectExceptionMessages().joinToString(separator)


/**
 * Return the receiver if it is of type [R], or null otherwise
 */
inline fun <reified R> Any?.takeIfIsInstance(): R? = if (this is R) this else null

