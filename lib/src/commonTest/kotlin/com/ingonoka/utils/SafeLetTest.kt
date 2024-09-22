package com.ingonoka.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class SafeLetTest {

    @Test
    fun testSafeLet2() {

        var a: String? = null
        var b: String? = "b"

        var c = safeLet(a, b) { p1, p2 ->
            p1 + p2
        }

        assertEquals(c, null)

        a = "a"
        b = "b"

        c = safeLet(a, b) { p1, p2 ->
            p1 + p2
        }

        assertEquals("ab", c)

    }

    @Test
    fun testSafeLet3() {

        var a: String? = null
        var b: String? = "b"
        var c: String? = "c"

        var d = safeLet(a, b, c) { p1, p2, p3 ->
            p1 + p2 + p3
        }

        assertEquals(d, null)

        a = "a"
        b = "b"
        c = "c"

        d = safeLet(a, b, c) { p1, p2, p3 ->
            p1 + p2 + p3
        }

        assertEquals("abc", d)

    }

    @Test
    fun testSafeLet4() {

        var a: String? = null
        var b: String? = "b"
        var c: String? = "c"
        var d: String? = "d"

        var e = safeLet(a, b, c, d) { p1, p2, p3, p4 ->
            p1 + p2 + p3 + p4
        }

        assertEquals(e, null)

        a = "a"
        b = "b"
        c = "c"
        d = "d"

        e = safeLet(a, b, c, d) { p1, p2, p3, p4 ->
            p1 + p2 + p3 + p4
        }

        assertEquals("abcd", e)

    }

    @Test
    fun testSafeLet5() {

        var a: String? = null
        var b: String? = "b"
        var c: String? = "c"
        var d: String? = "d"
        var e: String? = "e"

        var f = safeLet(a, b, c, d, e) { p1, p2, p3, p4, p5 ->
            p1 + p2 + p3 + p4 + p5
        }

        assertEquals(f, null)

        a = "a"
        b = "b"
        c = "c"
        d = "d"
        e = "e"

        f = safeLet(a, b, c, d, e) { p1, p2, p3, p4, p5 ->
            p1 + p2 + p3 + p4 + p5
        }

        assertEquals("abcde", f)

    }
}