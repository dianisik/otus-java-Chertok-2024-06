package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import ru.otus.core.repository.DataTemplateException;

@SuppressWarnings({"java:S1068", "java:S3011"})
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final List<Field> allFields;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = extractFields();
    }

    private List<Field> extractFields() {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .toList();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public Field getIdField() {
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
