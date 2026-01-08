package mocka.orm.persistence;

import jakarta.persistence.Column;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class JdbcBatchWriter {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private static final Pattern IDENTIFIER = Pattern.compile("^[a-zA-Z0-9_]+$");

    public JdbcBatchWriter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
    }

    public <T> void insertBatch(Class<T> entityType, List<T> batch) {
        if (batch == null || batch.isEmpty()) return;

        String tableName = sanitizeIdentifier(entityType.getSimpleName().toLowerCase());

        Field[] fields = Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> isSimpleType(f.getType()))
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .toArray(Field[]::new);

        List<String> columnNames = Arrays.stream(fields)
                .map(this::resolveColumnName)
                .peek(this::validateColumnName)
                .toList();

        String columns = String.join(", ", columnNames);
        String placeholders = columnNames.stream().map(name -> ":" + name).collect(Collectors.joining(", "));
        final String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, placeholders);

        SqlParameterSource[] batchParams = batch.stream().map(entity -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (value instanceof Enum<?> e) value = e.name();
                    params.addValue(resolveColumnName(field), value);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Failed to access field: " + field.getName(), ex);
                }
            }
            return params;
        }).toArray(SqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(sql, batchParams);
    }

    private void validateColumnName(String s) {
        if (!IDENTIFIER.matcher(s).matches())
            throw new IllegalArgumentException("Invalid column name: " + s);
    }

    private String sanitizeIdentifier(String s) {
        if (!IDENTIFIER.matcher(s).matches())
            throw new IllegalArgumentException("Invalid identifier: " + s);
        return s;
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
