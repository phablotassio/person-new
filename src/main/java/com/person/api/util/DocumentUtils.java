package com.person.api.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class DocumentUtils {

    private DocumentUtils() {
    }

    private static final int[] WEIGHT_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final String BLANK = "";
    private static final String REGEX_IS_INVALID_CPF = "(0){11}|(1){11}|(2){11}|(3){11}|(4){11}|(5){11}|(6){11}|(7){11}|(8){11}|(9){11}";
    private static final String REGEX_GROUP_MASK_CPF = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})";
    private static final String REGEX_MASK_CPF = "$1.$2.$3-$4";
    private static final String REGEX_NOT_NUMBERS = "\\W";
    private static final int BEGIN_INDEX = 0;
    private static final int END_INDEX = 9;

    private static final String REGEX_IS_INVALID_CNPJ = "(1){14}|(2){14}|(3){14}|(4){14}|(5){14}|(6){14}|(7){14}|(8){14}|(9){14}|(0){14}";
    private static Integer[] WEIGTH_CNPJ = new Integer[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isValidDocumentNumber(String documentNumber) {

        return isValidNaturalDocumentNumber(documentNumber) || isValidJuridicalDocumentNumber(documentNumber);
    }

    public static boolean isValidNaturalDocumentNumber(String cpf) {

        cpf = unmask(cpf);

        if (StringUtils.isBlank(cpf)) {
            return false;
        }

        if (Pattern.matches(REGEX_IS_INVALID_CPF, cpf)) {
            return false;
        }

        if (cpf.length() != 11) {
            return false;
        }

        Integer digitOne = calculateDigit(cpf.substring(BEGIN_INDEX, END_INDEX));
        Integer digitTwo = calculateDigit(cpf.substring(BEGIN_INDEX, END_INDEX) + digitOne);
        return cpf.equals(cpf.substring(BEGIN_INDEX, END_INDEX) + String.format("%s%s", digitOne, digitTwo));
    }

    public static String unmask(String documentNumber) {
        if (StringUtils.isBlank(documentNumber)) {
            return documentNumber;
        }

        return documentNumber.replaceAll(REGEX_NOT_NUMBERS, BLANK);
    }

    public static String maskCPF(String cpf) {
        if (isValidNaturalDocumentNumber(cpf)) {
            return unmask(cpf).replaceAll(REGEX_GROUP_MASK_CPF, REGEX_MASK_CPF);
        }

        return cpf;
    }

    public static boolean isValidJuridicalDocumentNumber(String documentNumber) {

        documentNumber = unmask(documentNumber);

        if (StringUtils.isBlank(documentNumber)) {
            return false;
        }

        if (documentNumber.matches(REGEX_IS_INVALID_CNPJ)) {
            return false;
        }

        if (documentNumber.length() != 14) {
            return false;
        }

        int firstVD = 0, secondVD = 0;

        for (int i = 0; i <= WEIGTH_CNPJ.length - 1; i++) {
            if (i <= 11) {

                Integer cnpjDigitInteger = Integer.valueOf(documentNumber.substring(i, i + 1));

                firstVD += WEIGTH_CNPJ[i + 1] * cnpjDigitInteger;
                secondVD += WEIGTH_CNPJ[i] * cnpjDigitInteger;
            } else {
                firstVD = firstVD % 11;
                firstVD = firstVD < 2 ? 0 : 11 - firstVD;

                secondVD += WEIGTH_CNPJ[i] * firstVD;

                secondVD = secondVD % 11;
                secondVD = secondVD < 2 ? 0 : 11 - secondVD;
            }
        }

        return documentNumber.substring(12, 14).equals(String.format("%s%s", firstVD, secondVD));
    }

    private static int calculateDigit(String str) {
        int sum = 0;
        for (int indice = str.length() - 1, digit; indice >= 0; indice--) {
            digit = Integer.parseInt(str.substring(indice, indice + 1));
            sum += digit * WEIGHT_CPF[WEIGHT_CPF.length - str.length() + indice];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

}