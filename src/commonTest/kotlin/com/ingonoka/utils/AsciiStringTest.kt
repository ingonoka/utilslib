package com.ingonoka.utils

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class AsciiStringTest {

    @Test
    fun testConstructor() {

        assertEquals("       FOO", AsciiString("FOO", 10, TextAlignment.ALIGN_RIGHT).s)
        assertEquals("FOO       ", AsciiString("FOO", 10, TextAlignment.ALIGN_LEFT).s)

        assertEquals(".......FOO", AsciiString("FOO", 10, TextAlignment.ALIGN_RIGHT, '.').s)
        assertEquals("FOO.......", AsciiString("FOO", 10, TextAlignment.ALIGN_LEFT, '.').s)

    }

    @Test
    fun testCopyInto() {

        val ba = UByteArray(10)
        AsciiString("FOO", 10, TextAlignment.ALIGN_RIGHT).copyInto(ba)

        assertContentEquals(ubyteArrayOf(0x20u, 0x20u, 0x20u, 0x20u, 0x20u, 0x20u, 0x20u, 0x46u, 0x4Fu, 0x4Fu), ba)

    }
}