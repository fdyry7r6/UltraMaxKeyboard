package helium314.keyboard.toptopy

/**
 * Unicode text styles used by Top Topy Studio.
 *
 * These are Unicode mathematical/alphanumeric symbols, not Android font files.
 * That means the converted text keeps its appearance in apps that support the
 * corresponding Unicode characters.
 */
object TopTopyTextStyles {
    enum class Style(val title: String, val sample: String) {
        NORMAL("Normal", "Top Topy"),
        BOLD("Bold", "𝐓𝐨𝐩 𝐓𝐨𝐩𝐲"),
        ITALIC("Italic", "𝑇𝑜𝑝 𝑇𝑜𝑝𝑦"),
        BOLD_ITALIC("Bold Italic", "𝑻𝒐𝒑 𝑻𝒐𝒑𝒚"),
        SANS("Sans", "𝖳𝗈𝗉 𝖳𝗈𝗉𝗒"),
        SANS_BOLD("Sans Bold", "𝗧𝗼𝗽 𝗧𝗼𝗽𝘆"),
        SANS_ITALIC("Sans Italic", "𝘛𝘰𝘱 𝘛𝘰𝘱𝘺"),
        SANS_BOLD_ITALIC("Sans Bold Italic", "𝙏𝙤𝙥 𝙏𝙤𝙥𝙮"),
        SCRIPT("Script", "𝒯𝑜𝓅 𝒯𝑜𝓅𝓎"),
        BOLD_SCRIPT("Bold Script", "𝓣𝓸𝓹 𝓣𝓸𝓹𝔂"),
        FRAKTUR("Fraktur", "𝔗𝔬𝔭 𝔗𝔬𝔭𝔶"),
        BOLD_FRAKTUR("Bold Fraktur", "𝕿𝖔𝖕 𝕿𝖔𝖕𝖞"),
        DOUBLE("Double Struck", "𝕋𝕠𝕡 𝕋𝕠𝕡𝕪"),
        MONO("Monospace", "𝚃𝚘𝚙 𝚃𝚘𝚙𝚢"),
        FULLWIDTH("Fullwidth", "Ｔｏｐ Ｔｏｐｙ"),
        CIRCLED("Circled", "Ⓣⓞⓟ Ⓣⓞⓟⓨ"),
        NEGATIVE_CIRCLED("Negative Circled", "🅣🅞🅟 🅣🅞🅟🅨"),
        SQUARED("Squared", "🅃🄾🄿 🅃🄾🄿🅈"),
        SMALL_CAPS("Small Caps", "ᴛᴏᴘ ᴛᴏᴘʏ"),
        UPSIDE_DOWN("Upside Down", "ʎdoʇ ʎdoʇ"),
        WIDE_SPACING("Wide Spacing", "T  o  p   T  o  p  y"),
        BUBBLE("Bubble", "Ⓣⓞⓟ Ⓣⓞⓟⓨ"),
        BRACKETED("Bracketed", "『Top Topy』"),
        AESTHETIC("Aesthetic", "Ｔｏｐ　Ｔｏｐｙ")
    }

    @JvmStatic
    fun transform(input: String, style: Style): String = when (style) {
        Style.NORMAL -> input
        Style.BOLD -> mathematical(input, 0x1D400, 0x1D41A, 0x1D7CE)
        Style.ITALIC -> mathematical(input, 0x1D434, 0x1D44E, null, mapOf('h' to 0x210E))
        Style.BOLD_ITALIC -> mathematical(input, 0x1D468, 0x1D482, null)
        Style.SANS -> mathematical(input, 0x1D5A0, 0x1D5BA, 0x1D7E2)
        Style.SANS_BOLD -> mathematical(input, 0x1D5D4, 0x1D5EE, 0x1D7EC)
        Style.SANS_ITALIC -> mathematical(input, 0x1D608, 0x1D622, 0x1D7E2)
        Style.SANS_BOLD_ITALIC -> mathematical(input, 0x1D63C, 0x1D656, 0x1D7EC)
        Style.SCRIPT -> script(input)
        Style.BOLD_SCRIPT -> mathematical(input, 0x1D4D0, 0x1D4EA, null)
        Style.FRAKTUR -> fraktur(input, false)
        Style.BOLD_FRAKTUR -> mathematical(input, 0x1D56C, 0x1D586, null)
        Style.DOUBLE -> doubleStruck(input)
        Style.MONO -> mathematical(input, 0x1D670, 0x1D68A, 0x1D7F6)
        Style.FULLWIDTH -> fullwidth(input)
        Style.CIRCLED, Style.BUBBLE -> circled(input, negative = false)
        Style.NEGATIVE_CIRCLED -> circled(input, negative = true)
        Style.SQUARED -> squared(input)
        Style.SMALL_CAPS -> smallCaps(input)
        Style.UPSIDE_DOWN -> upsideDown(input)
        Style.WIDE_SPACING -> wideSpacing(input)
        Style.BRACKETED -> "『$input』"
        Style.AESTHETIC -> fullwidth(input).replace('　', '　')
    }

    private fun mathematical(
        text: String,
        upperStart: Int,
        lowerStart: Int,
        digitStart: Int?,
        overrides: Map<Char, Int> = emptyMap()
    ): String = buildString {
        text.forEach { c ->
            val cp = overrides[c]
                ?: when {
                    c in 'A'..'Z' -> upperStart + (c.code - 'A'.code)
                    c in 'a'..'z' -> lowerStart + (c.code - 'a'.code)
                    digitStart != null && c in '0'..'9' -> digitStart + (c.code - '0'.code)
                    else -> -1
                }
            if (cp >= 0) appendCodePoint(cp) else append(c)
        }
    }

    private fun script(text: String): String {
        // Script has several historical gaps where Unicode uses BMP symbols.
        val upperFallback = mapOf(
            'B' to 0x212C, 'E' to 0x2130, 'F' to 0x2131,
            'H' to 0x210B, 'I' to 0x2110, 'L' to 0x2112,
            'M' to 0x2133, 'R' to 0x211B
        )
        val lowerFallback = mapOf('e' to 0x212F, 'g' to 0x210A, 'o' to 0x2134)
        return buildString {
            text.forEach { c ->
                val cp = when {
                    c in upperFallback -> upperFallback[c]!!
                    c in lowerFallback -> lowerFallback[c]!!
                    c in 'A'..'Z' -> 0x1D49C + (c.code - 'A'.code)
                    c in 'a'..'z' -> 0x1D4B6 + (c.code - 'a'.code)
                    else -> -1
                }
                if (cp >= 0) appendCodePoint(cp) else append(c)
            }
        }
    }

    private fun fraktur(text: String, bold: Boolean): String {
        if (bold) return mathematical(text, 0x1D56C, 0x1D586, null)
        val upperFallback = mapOf('C' to 0x212D, 'H' to 0x210C, 'I' to 0x2111, 'R' to 0x211C, 'Z' to 0x2128)
        return buildString {
            text.forEach { c ->
                val cp = when {
                    c in upperFallback -> upperFallback[c]!!
                    c in 'A'..'Z' -> 0x1D504 + (c.code - 'A'.code)
                    c in 'a'..'z' -> 0x1D51E + (c.code - 'a'.code)
                    else -> -1
                }
                if (cp >= 0) appendCodePoint(cp) else append(c)
            }
        }
    }

    private fun doubleStruck(text: String): String {
        val upperFallback = mapOf('C' to 0x2102, 'H' to 0x210D, 'I' to 0x2110, 'R' to 0x211D, 'Z' to 0x2124)
        return buildString {
            text.forEach { c ->
                val cp = when {
                    c in upperFallback -> upperFallback[c]!!
                    c in 'A'..'Z' -> 0x1D538 + (c.code - 'A'.code)
                    c in 'a'..'z' -> 0x1D552 + (c.code - 'a'.code)
                    c in '0'..'9' -> 0x1D7D8 + (c.code - '0'.code)
                    else -> -1
                }
                if (cp >= 0) appendCodePoint(cp) else append(c)
            }
        }
    }

    private fun fullwidth(text: String): String = text.map { c ->
        when {
            c in '!'..'~' -> (c.code + 0xFEE0).toChar()
            c == ' ' -> '　'
            else -> c
        }
    }.joinToString("")

    private fun circled(text: String, negative: Boolean): String = buildString {
        text.forEach { c ->
            when {
                !negative && c in 'A'..'Z' -> appendCodePoint(0x24B6 + c.code - 'A'.code)
                !negative && c in 'a'..'z' -> appendCodePoint(0x24D0 + c.code - 'a'.code)
                !negative && c in '0'..'9' -> append(listOf("⓪", "①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨")[c - '0'])
                negative && c in 'A'..'Z' -> appendCodePoint(0x1F150 + c.code - 'A'.code)
                negative && c in 'a'..'z' -> appendCodePoint(0x1F150 + c.code - 'a'.code)
                else -> append(c)
            }
        }
    }

    private fun squared(text: String): String = buildString {
        text.forEach { c ->
            when {
                c in 'A'..'Z' -> appendCodePoint(0x1F130 + c.code - 'A'.code)
                c in 'a'..'z' -> appendCodePoint(0x1F130 + c.code - 'a'.code)
                else -> append(c)
            }
        }
    }

    private fun smallCaps(text: String): String = text.map {
        when (it) {
            'a' -> 'ᴀ'; 'b' -> 'ʙ'; 'c' -> 'ᴄ'; 'd' -> 'ᴅ'; 'e' -> 'ᴇ'; 'f' -> 'ꜰ'; 'g' -> 'ɢ'
            'h' -> 'ʜ'; 'i' -> 'ɪ'; 'j' -> 'ᴊ'; 'k' -> 'ᴋ'; 'l' -> 'ʟ'; 'm' -> 'ᴍ'; 'n' -> 'ɴ'
            'o' -> 'ᴏ'; 'p' -> 'ᴘ'; 'q' -> 'ǫ'; 'r' -> 'ʀ'; 's' -> 's'; 't' -> 'ᴛ'; 'u' -> 'ᴜ'
            'v' -> 'ᴠ'; 'w' -> 'ᴡ'; 'x' -> 'x'; 'y' -> 'ʏ'; 'z' -> 'ᴢ'; else -> it
        }
    }.joinToString("")

    private fun upsideDown(text: String): String = text.reversed().map {
        when (it) {
            'a' -> 'ɐ'; 'b' -> 'q'; 'c' -> 'ɔ'; 'd' -> 'p'; 'e' -> 'ǝ'; 'f' -> 'ɟ'; 'g' -> 'ƃ'; 'h' -> 'ɥ'
            'i' -> 'ᴉ'; 'j' -> 'ɾ'; 'k' -> 'ʞ'; 'l' -> 'ʃ'; 'm' -> 'ɯ'; 'n' -> 'u'; 'o' -> 'o'; 'p' -> 'd'
            'q' -> 'b'; 'r' -> 'ɹ'; 's' -> 's'; 't' -> 'ʇ'; 'u' -> 'n'; 'v' -> 'ʌ'; 'w' -> 'ʍ'; 'x' -> 'x'
            'y' -> 'ʎ'; 'z' -> 'z'; '!' -> '¡'; '?' -> '¿'; '.' -> '˙'; else -> it
        }
    }.joinToString("")

    private fun wideSpacing(text: String): String = text.joinToString("  ")
}
