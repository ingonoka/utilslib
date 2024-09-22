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
 * Convert array of bytes to String, by interpreting each byte as a UTF-8 character.
 *
 * Only one-byte code units are allowed
 */
expect fun ByteArray.asUtf8ToString(index: Int = 0, length: Int = this.size - index): String

/**
 * Convert string into array of bytes in UTF-8 encoding
 */
expect fun String.toUtf8(): ByteArray