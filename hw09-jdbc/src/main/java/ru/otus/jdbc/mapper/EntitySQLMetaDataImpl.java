package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;
    private static final String FIELD_DELIMITER = ",";

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT %s FROM %s", concatAllFields(), entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "SELECT %s FROM %s WHERE %s = ? ", concatAllFields(), entityClassMetaData.getName(), getIdFieldName());
    }

    @Override
    public String getInsertSql() {
        return String.format(
                "INSERT INTO %s (%s) VALUES (%s) RETURNING %s",
                entityClassMetaData.getName(),
                concatAllFieldsWithoutId(),
                concatAllValuesWithoutId(),
                getIdFieldName());
    }

    @Override
    public String getUpdateSql() {
        return String.format(
                "UPDATE %s SET (%s) WHERE %s = ? ",
                entityClassMetaData.getName(), concatAllUpdatedFields(), getIdFieldName());
    }

    private String concatAllUpdatedFields() {
        StringJoiner queryValues = new StringJoiner("= ?, ", "", "= ?");
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        for (Field field : fields) {
            queryValues.add(field.getName());
        }
        return queryValues.toString();
    }

    private String concatAllValuesWithoutId() {
        StringJoiner queryValues = new StringJoiner(FIELD_DELIMITER);
        var count = entityClassMetaData.getFieldsWithoutId().size();
        for (int i = 0; i < count; i++) {
            queryValues.add("?");
        }
        return queryValues.toString();
    }

    private String concatAllFields() {
        List<Field> fields = entityClassMetaData.getAllFields();
        return concatFields(fields);
    }

    private String concatAllFieldsWithoutId() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return concatFields(fields);
    }

    private String concatFields(List<Field> fields) {
        StringJoiner queryFields = new StringJoiner(FIELD_DELIMITER);
        for (Field field : fields) {
            queryFields.add(field.getName());
        }

        return queryFields.toString();
    }

    private String getIdFieldName() {
        return entityClassMetaData.getIdField().getName();
    }
}
