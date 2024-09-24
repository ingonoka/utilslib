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
 * Calculate the Luhn check digit for a list of decimal digits
 */
fun List<Int>.luhnCheckSum() =
    reversed()
        .mapIndexed { index, num ->
            if (index % 2 == 0)
                num + num
            else
                num
        }.sumOf {
            if (it > 9)
                it - 9
            else
                it
        } * 9 % 10

/**
 * Calculate the Luhn check digit for a decimal [Long]
 */
fun Long.luhnCheckSum() =
    mutableListOf<Int>().apply {
        toDecimalDigits(this@luhnCheckSum)
    }.toList().luhnCheckSum()

/**
 * Calculate the Luhn Check digit for a string of decimal characters
 */
fun String.luhnCheckSum() = map { c -> c.code - '0'.code }.luhnCheckSum()

/**
 * Check Luhn check digit assuming that [String] only contains decimal digits
 */
fun String.validateLuhnChecksum() = dropLast(1).luhnCheckSum() == (last().code - '0'.code)

/**
 * Check Luhn check digit of a list of decimal digits
 */
fun List<Int>.validateLuhnChecksum() = dropLast(1).luhnCheckSum() == last()

/**
 * Check Luhn check digit of a decimal [Long]
 */
fun Long.validateLuhnChecksum() = (this/10L).luhnCheckSum() == (this % 10).toInt()


private fun MutableList<Int>.toDecimalDigits(num: Long) {
    val nextNum = num / 10L
    if ( nextNum != 0L) toDecimalDigits(nextNum)
    add((num % 10).toInt())
}

