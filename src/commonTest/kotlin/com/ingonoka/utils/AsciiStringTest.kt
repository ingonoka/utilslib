package com.ingonoka.utils

import com.ingonoka.hexutils.hexToBytes
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class AsciiStringTest {

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun serializeProtoBuf() {

        @Serializable
        data class Test(val s: AsciiString)

        val t = Test(AsciiString("A"))
        val ba = "0a0141".hexToBytes()
        val json = "{\"s\":\"A\"}"
        assertContentEquals( ba , ProtoBuf.encodeToByteArray(t))
        assertEquals( t, ProtoBuf.decodeFromByteArray<Test>(ba))

        assertEquals(json, Json.encodeToString(t))
        assertEquals(t, Json.decodeFromString<Test>(json))

    }

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