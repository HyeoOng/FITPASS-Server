package com.ssafy.fitpass.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlCharacterEscape extends CharacterEscapes {

    public final int [] asciiEscapes;

    public HtmlCharacterEscape() {
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

        // 추가 특수 문자
//        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;  // Ampersand
//        asciiEscapes['/'] = CharacterEscapes.ESCAPE_CUSTOM;  // Slash
//        asciiEscapes[';'] = CharacterEscapes.ESCAPE_CUSTOM;  // Semicolon
//        asciiEscapes['%'] = CharacterEscapes.ESCAPE_CUSTOM;  // Percent Sign
//        asciiEscapes['='] = CharacterEscapes.ESCAPE_CUSTOM;  // Equals Sign
//        asciiEscapes['{'] = CharacterEscapes.ESCAPE_CUSTOM;  // Curly Brace (Left)
//        asciiEscapes['}'] = CharacterEscapes.ESCAPE_CUSTOM;  // Curly Brace (Right)
//        asciiEscapes['['] = CharacterEscapes.ESCAPE_CUSTOM;  // Square Bracket (Left)
//        asciiEscapes[']'] = CharacterEscapes.ESCAPE_CUSTOM;  // Square Bracket (Right)
//        asciiEscapes['|'] = CharacterEscapes.ESCAPE_CUSTOM;  // Pipe
//        asciiEscapes[':'] = CharacterEscapes.ESCAPE_CUSTOM;  // Colon
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        return new
                SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
    }
}
