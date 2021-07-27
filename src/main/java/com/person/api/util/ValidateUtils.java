package com.person.api.util;

import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.MessageErrorImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ValidateUtils {

    private static Validator validator;
    static String FIELD_NAME = "interpolatedMessage";
    private static final int MIN_AGE = 18;

    ValidateUtils(@Qualifier("validator") Validator validator) {
        ValidateUtils.validator = validator;
    }

    public static <T> List<String> validateObject(T object) {

        return validator.validate(object).stream().map(ValidateUtils::getError).collect(Collectors.toList());
    }

    public static boolean personHaveMinAge(Date birthDate) {

        return Period.between(birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(), LocalDate.now()).getYears() >= MIN_AGE;

    }

    public static void validateDocumentNumberIfExists(Map<String, Object> fields) {
        String DOCUMENT_NUMBER = "documentNumber";
        CharSequence documentNumber = ObjectUtils.getFieldValue(fields, DOCUMENT_NUMBER, CharSequence.class);

        if (StringUtils.isNotBlank(documentNumber) && !DocumentUtils.isValidDocumentNumber((String.valueOf(documentNumber)))) {
            throw new AbstractRuntimeException(HttpStatus.UNPROCESSABLE_ENTITY, MessageErrorImpl.INVALID_DOCUMENT_NUMBER, String.valueOf(documentNumber));
        }
    }

    private static String getError(ConstraintViolation constraintViolation) {

        try {
            return replaceChars(String.valueOf(FieldUtils.readField(constraintViolation, FIELD_NAME, true)), constraintViolation.getPropertyPath().toString());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new AbstractRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static String replaceChars(String source, String charsToReplace) {
        return source.replaceAll("(\\{\\d\\})", charsToReplace);
    }
}