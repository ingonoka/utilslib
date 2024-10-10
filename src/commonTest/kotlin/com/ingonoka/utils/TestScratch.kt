package com.ingonoka.utils

import kotlin.test.Test

class TestScratch {

    @Test
    fun test() {
        (0x0u.countLeadingZeroBits() / 8)

        println(0x0u
            .toString(2)
            .padStart(32, '0')
            .chunked(4)
            .joinToString(" ", "[", "]")
        )
    }
}

