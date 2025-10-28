package mocka.persistence;

import jakarta.persistence.Column;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JdbcBatchWriter {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public JdbcBatchWriter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedJdbcTemplate = namedParameterJdbcTemplate;
    }

    public <T> void insertBatch(Class<T> entityType, List<T> batch) {
        if (batch == null || batch.isEmpty()) return;

        // ✅ 내부 코드에서만 생성 → 외부 입력 아님 (사실상 안전)
        String tableName = entityType.getSimpleName().toLowerCase();

        Field[] fields = Arrays.stream(entityType.getDeclaredFields())
                .filter(f -> isSimpleType(f.getType()))
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .toArray(Field[]::new);

        List<String> columnNames = Arrays.stream(fields)
                .map(this::resolveColumnName)
                .toList();

        String columns = String.join(", ", columnNames);
        String placeholders = columnNames.stream()
                .map(name -> ":" + name)
                .collect(Collectors.joining(", "));

        // ✅ NamedParameterJdbcTemplate용 SQL
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        // ✅ 파라미터 매핑
        List<Map<String, Object>> paramMaps = new ArrayList<>();
        for (T entity : batch) {
            Map<String, Object> paramMap = new HashMap<>();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value instanceof Enum<?> e) value = e.name();
                    paramMap.put(resolveColumnName(field), value);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Failed to access field: " + field.getName(), e);
                }
            }
            paramMaps.add(paramMap);
        }

        // ✅ Batch Insert 실행
        namedJdbcTemplate.batchUpdate(sql, paramMaps.toArray(new Map[0]));
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
