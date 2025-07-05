package com.taskmanagement.core.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JSONUtil {

    public static <T> String getJsonPropertyName(ConstraintViolation<T> violation) {
        String propertyPath = violation.getPropertyPath().toString();

        try {
            // Use reflection to get the field or method annotated with @JsonProperty
            Class<?> rootClass = violation.getRootBeanClass();
            Field field = rootClass.getDeclaredField(propertyPath);
            JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);

            if (jsonPropertyAnnotation != null) {
                return jsonPropertyAnnotation.value();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return propertyPath; // Fallback to the property path if @JsonProperty is not found
    }

    public static String getJsonPropertyName(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == JsonProperty.class) {
                    return ((JsonProperty) annotation).value();
                }
            }
        } catch (NoSuchFieldException ignored) {

        }
        return fieldName;
    }
}
