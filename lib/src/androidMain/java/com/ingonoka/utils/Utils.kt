/*
 * Copyright (c) 2021. Ingo Noka
 * This file belongs to project utils-mp.
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 *
 */
@file:JvmName("Utils")
@file:JvmMultifileClass

package com.ingonoka.utils

actual fun String.toByteArray(): ByteArray = toByteArray(Charsets.UTF_8)

actual fun String.toCharacterArray(): CharArray = this.toCharArray()

actual fun currentTimeMillis(): Long = System.currentTimeMillis()