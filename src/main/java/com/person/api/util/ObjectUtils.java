package com.person.api.util;

import com.person.api.exception.AbstractRuntimeException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public abstract class ObjectUtils {

    private ObjectUtils() {
    }

    public static <T> T patch(Map<String, Object> fields, T target) {
        fields.forEach((k, v) -> {
            try {
                ObjectUtils.applyChangesOnObject(k, v, target);
            } catch (ParseException | IllegalAccessException | InstantiationException e) {
                throw new AbstractRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });

        return target;
    }

    private static <T> void applyChangesOnObject(String property, T source, T target) throws ParseException, IllegalAccessException, InstantiationException {

        BeanWrapper destWrap = PropertyAccessorFactory.forBeanPropertyAccess(target);
        destWrap.setAutoGrowNestedPaths(true);

        Object propertyValue;
        try {
            propertyValue = destWrap.getPropertyValue(property);
        } catch (NotReadablePropertyException e) {
            return;
        }
        if (source == null) {
            destWrap.setPropertyValue(property, null);
        } else if (source instanceof List<?> && propertyValue instanceof List<?>) {
            List<?> srcList = (List<?>) source;
            List<?> destList = (List<?>) propertyValue;
            if (srcList.size() == destList.size()) {
                for (int i = 0; i < srcList.size(); i++) {
                    if (srcList.get(i).getClass() == LinkedHashMap.class) {
                        HashMap<Object, Object> properties = (HashMap<Object, Object>) srcList.get(i);
                        final Object destObject = destList.get(i);
                        BeanWrapper desObjectWrap = PropertyAccessorFactory.forBeanPropertyAccess(destObject);
                        desObjectWrap.setPropertyValues(properties);
                    }
                }
            }
        } else if (source.getClass() == LinkedHashMap.class) {

            if (Objects.isNull(propertyValue)) {
                Class<?> propertyType = destWrap.getPropertyType(property);
                assert propertyType != null;
                propertyValue = propertyType.newInstance();
                destWrap.setPropertyValue(property, propertyValue);
            }

            HashMap<Object, Object> properties = (HashMap<Object, Object>) source;
            BeanWrapper desObjectWrap = PropertyAccessorFactory.forBeanPropertyAccess(propertyValue);
            desObjectWrap.setPropertyValues(properties);
        } else {
            if (destWrap.getPropertyType(property) == Date.class && source instanceof String) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(source));
                destWrap.setPropertyValue(property, date);
            } else if (destWrap.getPropertyType(property) == LocalDate.class && source instanceof String) {
                destWrap.setPropertyValue(property, LocalDate.parse(String.valueOf(source)));
            } else {
                destWrap.setPropertyValue(property, source);
            }
        }

    }

    public static <T> T getFieldValue(Map<String, Object> fields, String nameField, Class<T> object) {
        try {
            return object.cast(fields.get(nameField));
        } catch (ClassCastException ex) {
            throw new AbstractRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}