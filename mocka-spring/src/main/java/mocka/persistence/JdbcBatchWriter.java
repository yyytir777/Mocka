package mocka.persistence;

import jakarta.persistence.Column;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JdbcBatchWriter {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBatchWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> void insertBatch(Class<T> entityType, List<T> batch) {
        if (batch == null || batch.isEmpty()) return;

        String tableName = entityType.getSimpleName().toLowerCase();

        Field[] fields = Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> isSimpleType(f.getType()))
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .toArray(Field[]::new);

        // resolve name field of @Column annotation
        String columns = Arrays.stream(fields)
                .map(this::resolveColumnName)
                .collect(Collectors.joining(", "));

        String placeholders = String.join(", ",
                java.util.Arrays.stream(fields).map(f -> "?").toList());

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        // 🔸 3. batch insert 실행
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T entity = batch.get(i);
                try {
                    for (int j = 0; j < fields.length; j++) {
                        fields[j].setAccessible(true);
                        if(fields[j].get(entity) instanceof Enum<?>) ps.setObject(j + 1, ((Enum<?>) fields[j].get(entity)).name());
                        else ps.setObject(j + 1, fields[j].get(entity));
                    }
                } catch (IllegalAccessException e) {
                    throw new SQLException("Failed to map entity fields", e);
                }
            }

            @Override
            public int getBatchSize() {
                return batch.size();
            }
        });
    }

    private String resolveColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if( column != null && !column.name().isEmpty()) return column.name();
        else return toSnakeCase(field.getName());
    }

    private String toSnakeCase(String name) {
        return name.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    private boolean isSimpleType(Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || Number.class.isAssignableFrom(type)
                || Boolean.class.equals(type)
                || java.util.Date.class.isAssignableFrom(type)
                || java.time.temporal.Temporal.class.isAssignableFrom(type)
                || type.isEnum();
    }
}
