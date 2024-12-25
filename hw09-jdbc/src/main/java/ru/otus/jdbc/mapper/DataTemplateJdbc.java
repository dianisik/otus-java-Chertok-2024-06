package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings({"java:S1068", "java:S3011"})
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private EntityClassMetaDataImpl<T> entityClassMetaData;
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private static final String ERROR_FIELD_TYPE = "Not valid type of field";
    private static final String ERROR_UNEXPECTED = "Unexpected error";

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    EntityClassMetaData<T> metaData = getObjMetaData();
                    return getObjFields(rs, metaData);
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    private T getObjFields(ResultSet rs, EntityClassMetaData<T> metaData)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = metaData.getConstructor();
        T obj = constructor.newInstance();
        setObjFields(metaData, obj, rs);
        return obj;
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var clients = new ArrayList<T>();
                    EntityClassMetaData<T> metaData = getObjMetaData();

                    try {
                        while (rs.next()) {
                            T obj = getObjFields(rs, metaData);
                            clients.add(obj);
                        }
                        return clients;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException(ERROR_UNEXPECTED));
    }

    @Override
    public long insert(Connection connection, T client) {
        EntityClassMetaData<T> metaData = getObjMetaData();
        List<Object> params = getInsertParams(metaData, client);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    private List<Object> getInsertParams(EntityClassMetaData<T> metaData, T client) {
        var fields = metaData.getFieldsWithoutId().stream().map(Field::getName).toList();
        var params = new ArrayList<>();
        for (String field : fields) {
            try {
                Field f = client.getClass().getDeclaredField(field);
                f.setAccessible(true);
                params.add(f.get(client));
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }
        return params;
    }

    @SuppressWarnings("unchecked")
    private EntityClassMetaData<T> getObjMetaData() {
        if (entityClassMetaData == null) {
            try {
                Field field = entitySQLMetaData.getClass().getDeclaredField("entityClassMetaData");
                field.setAccessible(true);
                entityClassMetaData = (EntityClassMetaDataImpl<T>) field.get(entitySQLMetaData);
                return entityClassMetaData;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        } else return entityClassMetaData;
    }

    private void setObjFields(EntityClassMetaData<T> metaData, T obj, ResultSet rs) {
        var fields = metaData.getAllFields();
        Object value;
        for (Field field : fields) {
            var fieldName = field.getName();
            try {
                Field objField = obj.getClass().getDeclaredField(fieldName);
                objField.setAccessible(true);
                Class<?> fieldType = field.getType();

                if (fieldType.equals(Long.class)) {
                    value = rs.getLong(fieldName);
                } else if (fieldType.equals(String.class)) {
                    value = rs.getString(fieldName);
                } else {
                    throw new DataTemplateException(ERROR_FIELD_TYPE);
                }
                objField.set(obj, value);
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }
    }
}
