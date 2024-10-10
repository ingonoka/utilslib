package com.ingonoka.utils

import kotlin.jvm.JvmInline

enum class TextAlignment { ALIGN_LEFT, ALIGN_RIGHT }

val asciiPrintable = mapOf(
    ' ' to 0x20u.toUByte(), '!' to 0x21u.toUByte(), '"' to 0x22u.toUByte(), '#' to 0x23u.toUByte(),
    '$' to 0x24u.toUByte(), '%' to 0x25u.toUByte(), '&' to 0x26u.toUByte(), '\'' to 0x27u.toUByte(),
    '(' to 0x28u.toUByte(), ')' to 0x29u.toUByte(), '*' to 0x2Au.toUByte(), '+' to 0x2Bu.toUByte(),
    ',' to 0x2Cu.toUByte(), '-' to 0x2Du.toUByte(), '.' to 0x2Eu.toUByte(), '/' to 0x2Fu.toUByte(),
    '0' to 0x30u.toUByte(), '1' to 0x31u.toUByte(), '2' to 0x32u.toUByte(), '3' to 0x33u.toUByte(),
    '4' to 0x34u.toUByte(), '5' to 0x35u.toUByte(), '6' to 0x36u.toUByte(), '7' to 0x37u.toUByte(),
    '8' to 0x38u.toUByte(), '9' to 0x39u.toUByte(), ':' to 0x3Au.toUByte(), ';' to 0x3Bu.toUByte(),
    '<' to 0x3Cu.toUByte(), '=' to 0x3Du.toUByte(), '>' to 0x3Eu.toUByte(), '?' to 0x3Fu.toUByte(),
    '@' to 0x40u.toUByte(), 'A' to 0x41u.toUByte(), 'B' to 0x42u.toUByte(), 'C' to 0x43u.toUByte(),
    'D' to 0x44u.toUByte(), 'E' to 0x45u.toUByte(), 'F' to 0x46u.toUByte(), 'G' to 0x47u.toUByte(),
    'H' to 0x48u.toUByte(), 'I' to 0x49u.toUByte(), 'J' to 0x4Au.toUByte(), 'K' to 0x4Bu.toUByte(),
    'L' to 0x4Cu.toUByte(), 'M' to 0x4Du.toUByte(), 'N' to 0x4Eu.toUByte(), 'O' to 0x4Fu.toUByte(),
    'P' to 0x50u.toUByte(), 'Q' to 0x51u.toUByte(), 'R' to 0x52u.toUByte(), 'S' to 0x53u.toUByte(),
    'T' to 0x54u.toUByte(), 'U' to 0x55u.toUByte(), 'V' to 0x56u.toUByte(), 'W' to 0x57u.toUByte(),
    'X' to 0x58u.toUByte(), 'Y' to 0x59u.toUByte(), 'Z' to 0x5Au.toUByte(), '[' to 0x5Bu.toUByte(),
    '\\' to 0x5Cu.toUByte(), ']' to 0x5Du.toUByte(), '^' to 0x5Eu.toUByte(), '_' to 0x5Fu.toUByte(),
    '@' to 0x60u.toUByte(), 'a' to 0x61u.toUByte(), 'b' to 0x62u.toUByte(), 'c' to 0x63u.toUByte(),
    'd' to 0x64u.toUByte(), 'e' to 0x65u.toUByte(), 'f' to 0x66u.toUByte(), 'g' to 0x67u.toUByte(),
    'h' to 0x68u.toUByte(), 'i' to 0x69u.toUByte(), 'j' to 0x6Au.toUByte(), 'k' to 0x6Bu.toUByte(),
    'l' to 0x6Cu.toUByte(), 'm' to 0x6Du.toUByte(), 'n' to 0x6Eu.toUByte(), 'o' to 0x6Fu.toUByte(),
    'p' to 0x70u.toUByte(), 'q' to 0x71u.toUByte(), 'r' to 0x72u.toUByte(), 's' to 0x73u.toUByte(),
    't' to 0x74u.toUByte(), 'u' to 0x75u.toUByte(), 'v' to 0x76u.toUByte(), 'w' to 0x77u.toUByte(),
    'x' to 0x78u.toUByte(), 'y' to 0x79u.toUByte(), 'z' to 0x7Au.toUByte(), '{' to 0x7Bu.toUByte(),
    '|' to 0x7Cu.toUByte(), '}' to 0x7Du.toUByte(), '~' to 0x7Eu
)

val asciiPrintableCode = mapOf(
    0x20u.toUByte() to ' ', 0x21u.toUByte() to '!', 0x22u.toUByte() to '"', 0x23u.toUByte() to '#',
    0x24u.toUByte() to '$', 0x25u.toUByte() to '%', 0x26u.toUByte() to '&', 0x27u.toUByte() to '\'',
    0x28u.toUByte() to '(', 0x29u.toUByte() to ')', 0x2Au.toUByte() to '*', 0x2Bu.toUByte() to '+',
    0x2Cu.toUByte() to ',', 0x2Du.toUByte() to '-', 0x2Eu.toUByte() to '.', 0x2Fu.toUByte() to '/',
    0x30u.toUByte() to '0', 0x31u.toUByte() to '1', 0x32u.toUByte() to '2', 0x33u.toUByte() to '3',
    0x34u.toUByte() to '4', 0x35u.toUByte() to '5', 0x36u.toUByte() to '6', 0x37u.toUByte() to '7',
    0x38u.toUByte() to '8', 0x39u.toUByte() to '9', 0x3Au.toUByte() to ':', 0x3Bu.toUByte() to ';',
    0x3Cu.toUByte() to '<', 0x3Du.toUByte() to '=', 0x3Eu.toUByte() to '>', 0x3Fu.toUByte() to '?',
    0x40u.toUByte() to '@', 0x41u.toUByte() to 'A', 0x42u.toUByte() to 'B', 0x43u.toUByte() to 'C',
    0x44u.toUByte() to 'D', 0x45u.toUByte() to 'E', 0x46u.toUByte() to 'F', 0x47u.toUByte() to 'G',
    0x48u.toUByte() to 'H', 0x49u.toUByte() to 'I', 0x4Au.toUByte() to 'J', 0x4Bu.toUByte() to 'K',
    0x4Cu.toUByte() to 'L', 0x4Du.toUByte() to 'M', 0x4Eu.toUByte() to 'N', 0x4Fu.toUByte() to 'O',
    0x50u.toUByte() to 'P', 0x51u.toUByte() to 'Q', 0x52u.toUByte() to 'R', 0x53u.toUByte() to 'S',
    0x54u.toUByte() to 'T', 0x55u.toUByte() to 'U', 0x56u.toUByte() to 'V', 0x57u.toUByte() to 'W',
    0x58u.toUByte() to 'X', 0x59u.toUByte() to 'Y', 0x5Au.toUByte() to 'Z', 0x5Bu.toUByte() to '[',
    0x5Cu.toUByte() to '\\', 0x5Du.toUByte() to ']', 0x5Eu.toUByte() to '^', 0x5Fu.toUByte() to '_',
    0x60u.toUByte() to '@', 0x61u.toUByte() to 'a', 0x62u.toUByte() to 'b', 0x63u.toUByte() to 'c',
    0x64u.toUByte() to 'd', 0x65u.toUByte() to 'e', 0x66u.toUByte() to 'f', 0x67u.toUByte() to 'g',
    0x68u.toUByte() to 'h', 0x69u.toUByte() to 'i', 0x6Au.toUByte() to 'j', 0x6Bu.toUByte() to 'k',
    0x6Cu.toUByte() to 'l', 0x6Du.toUByte() to 'm', 0x6Eu.toUByte() to 'n', 0x6Fu.toUByte() to 'o',
    0x70u.toUByte() to 'p', 0x71u.toUByte() to 'q', 0x72u.toUByte() to 'r', 0x73u.toUByte() to 's',
    0x74u.toUByte() to 't', 0x75u.toUByte() to 'u', 0x76u.toUByte() to 'v', 0x77u.toUByte() to 'w',
    0x78u.toUByte() to 'x', 0x79u.toUByte() to 'y', 0x7Au.toUByte() to 'z', 0x7Bu.toUByte() to '{',
    0x7Cu.toUByte() to '|', 0x7Du.toUByte() to '}', 0x7Eu.toUByte() to '~'
)

/**
 * Inline value class wrapping a [String].  Class ensures that string
 * only contains characters in the printable ASCII range between 0x20 to 0x7E
 */
@JvmInline
value class AsciiString(val s: String) {

    init {
        require(s.all { it in ' '..'~' })
    }

    /**
     * Construct an [AsciiString] from [s] which is padded according to [length], [alignment] and [padChar]
     *
     * @param s The string to be padded.
     * @param length The length of the padded ascii string. (default: length of [s], i.e., no padding)
     * @param alignment [TextAlignment] to the left or right (default: right)
     * @param padChar The character used for padding (default: space)
     */
    constructor(
        s: String, length: Int = s.length,
        alignment: TextAlignment = TextAlignment.ALIGN_RIGHT,
        padChar: Char = ' '
    ) : this(
        when (alignment) {
            TextAlignment.ALIGN_RIGHT -> s.padStart(length, padChar = padChar)
            TextAlignment.ALIGN_LEFT -> s.padEnd(length, padChar = padChar)
        }
    )

    /**
     * Copy the [AsciiString] into destination[ba], which is a [UByteArray] starting at
     * [offset] in destination.
     *
     * @throws IllegalArgumentException If the size of [ba] is less than the length of the string
     * @return The index within [ba] that follows the last byte that was copied.
     */
    fun copyInto(ba: UByteArray, offset: Int = 0): Int {

        require(ba.size - offset <= s.length)

        for (i in s.indices) {
            ba[i + offset] = asciiPrintable.getValue(s[i])
        }

        return offset + s.length
    }
}

fun UByteArray.readAsciiString(offset: Int = 0, length: Int): Result<AsciiString> = try {
    require(offset + length <= size)
    val ca = CharArray(length)
    for (i in 0..<length) {
        ca[i] = asciiPrintableCode[this[offset + i]] ?: throw CharacterCodingException()
    }
    val s = AsciiString(ca.concatToString())

    Result.success(s)

} catch (e: Exception) {

    Result.failure(e)
}