//package mocka.batch.persistence;
//
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class JdbcBatchPersistence {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public JdbcBatchPersistence(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public <T> void insertBatch(Class<T> entityType, List<T> batch) {
//        if (batch == null || batch.isEmpty()) return;
//
//        String tableName = entityType.getSimpleName().toLowerCase();
//
//        Field[] fields = Arrays.stream(entityType.getDeclaredFields())
//                .filter(f -> isSimpleType(f.getType())) // ✅ 관계 필드 제외
//                .filter(f -> !f.getName().equalsIgnoreCase("id")) // ✅ id 제외
//                .toArray(Field[]::new);
//
//        String columns = String.join(", ",
//                java.util.Arrays.stream(fields).map(Field::getName).toList());
//
//        String placeholders = String.join(", ",
//                java.util.Arrays.stream(fields).map(f -> "?").toList());
//
//        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
//
//        // 🔸 3. batch insert 실행
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                T entity = batch.get(i);
//                try {
//                    for (int j = 0; j < fields.length; j++) {
//                        fields[j].setAccessible(true);
//                        ps.setObject(j + 1, fields[j].get(entity));
//                    }
//                } catch (IllegalAccessException e) {
//                    throw new SQLException("Failed to map entity fields", e);
//                }
//            }
//
//            @Override
//            public int getBatchSize() {
//                return batch.size();
//            }
//        });
//    }
//
//    private boolean isSimpleType(Class<?> type) {
//        return type.isPrimitive()
//                || type.equals(String.class)
//                || Number.class.isAssignableFrom(type)
//                || Boolean.class.equals(type)
//                || java.util.Date.class.isAssignableFrom(type)
//                || java.time.temporal.Temporal.class.isAssignableFrom(type)
//                || type.isEnum();
//    }
//}
