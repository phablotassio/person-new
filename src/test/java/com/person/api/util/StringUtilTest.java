package com.person.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilTest {

    @Test
    void removeNonNumericCharacters() {

        String result = StringUtil.removeNonNumericCharacters("321dsadasdas-.321321--sadsad.sa321.321321321321.321321312");

        assertTrue(org.apache.commons.lang3.StringUtils.isAlphanumeric(result));
        assertEquals("321321321321321321321321321321312", result);

    }
}