package com.dsl.ast.parser;

public interface LayoutCharacters {

    /**
     * Tabulator character.
     */
    final static byte TAB    = 0x8;

    /**
     * End of input character. Used as a sentinel to denote the character one beyond the last defined character in a
     * source file.
     */
    final static byte EOI    = 0x1A;
    
}
