package com.person.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentUtilsTest {

    @Test
    void isValid() {
        assertTrue(DocumentUtils.isValidDocumentNumber("514.142.476-64"));
        assertTrue(DocumentUtils.isValidDocumentNumber("25.265.752/0001-05"));

    }


    @Test
    void isValidWithInvalidDocumentNumber() {
        assertFalse(DocumentUtils.isValidDocumentNumber("514.142.476-88"));
        assertFalse(DocumentUtils.isValidDocumentNumber("25.265.752/0001-77"));

    }

    @Test
    void isValidCPFTest() {
        assertTrue(DocumentUtils.isValidNaturalDocumentNumber("514.142.476-64"));
        assertTrue(DocumentUtils.isValidNaturalDocumentNumber("972.102.141-51"));
        assertTrue(DocumentUtils.isValidNaturalDocumentNumber("514.142.47664"));


        assertFalse(DocumentUtils.isValidNaturalDocumentNumber(null));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber(""));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("51 4e 14 2f47664"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("514.142.4766443456"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("514"));

        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("00000000000"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("11111111111"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("22222222222"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("33333333333"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("44444444444"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("55555555555"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("66666666666"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("77777777777"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("88888888888"));
        assertFalse(DocumentUtils.isValidNaturalDocumentNumber("99999999999"));
    }


    @Test
    void unMaskCpfTest() {
        assertEquals("51414247664", DocumentUtils.unmask("514.142.476-64"));
        assertEquals("97210214151", DocumentUtils.unmask("972.102.141-51"));

        assertNull(DocumentUtils.unmask(null));
        assertEquals("", DocumentUtils.unmask(""));
        assertEquals("514e142f47664", DocumentUtils.unmask("51 4e 14 2f47664"));
        assertEquals("51414247664", DocumentUtils.unmask("514.142.47664"));
        assertEquals("a51414247664", DocumentUtils.unmask("a514.142.47664"));

        assertEquals("00000000000", DocumentUtils.unmask("00000000000"));
        assertEquals("11111111111", DocumentUtils.unmask("11111111111"));
    }

    @Test
    void umaskCpfTest() {
        assertEquals("514.142.476-64", DocumentUtils.maskCPF("51414247664"));
        assertEquals("972.102.141-51", DocumentUtils.maskCPF("97210214151"));

        assertNull(DocumentUtils.maskCPF(null));
        assertEquals("", DocumentUtils.maskCPF(""));
        assertEquals("51 4e 14 2f47664", DocumentUtils.maskCPF("51 4e 14 2f47664"));
        assertEquals("514.142.476-64", DocumentUtils.maskCPF("514.142.47664"));

        assertEquals("00000000000", DocumentUtils.maskCPF("00000000000"));
        assertEquals("11111111111", DocumentUtils.maskCPF("11111111111"));
    }


    @Test
    void validCNPJTest() {
        assertTrue(DocumentUtils.isValidJuridicalDocumentNumber("13.347.016/0001-17"));
        assertTrue(DocumentUtils.isValidJuridicalDocumentNumber("13347016000117"));
        assertTrue(DocumentUtils.isValidJuridicalDocumentNumber("34238864000168"));
        assertFalse(DocumentUtils.isValidJuridicalDocumentNumber("13347016000118"));
        assertFalse(DocumentUtils.isValidJuridicalDocumentNumber(""));
        assertFalse(DocumentUtils.isValidJuridicalDocumentNumber("1334701600011a"));
        assertFalse(DocumentUtils.isValidJuridicalDocumentNumber("11111111111111"));

    }

    @Test
    void unMaskCNPJTest() {
        assertEquals("13347016000117", DocumentUtils.unmask("13.347.016/0001-17"));
        assertEquals("13347016000117", DocumentUtils.unmask("13347016000117"));
        assertEquals("13347a01a600a0s1d17", DocumentUtils.unmask("13347a01a600a0s1d17"));
        assertNull(DocumentUtils.unmask(null));
        assertEquals("", DocumentUtils.unmask(""));

    }


}