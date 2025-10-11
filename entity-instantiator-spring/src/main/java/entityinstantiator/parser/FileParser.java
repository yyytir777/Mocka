package entityinstantiator.parser;


import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public interface FileParser {
    <T> T parse(InputStream inputStream, Class<T> clazz);

    @SuppressWarnings({"unchecked", "rawtypes"})
    default Object convertValue(Class<?> type, Object rawValue) {
        if (rawValue == null) return null;

        String value = rawValue.toString().trim();
        if (value.isEmpty()) return null;

        try {
            // Primitive & Wrapper types
            if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
            if (type == short.class || type == Short.class) return Short.parseShort(value);
            if (type == int.class || type == Integer.class) return Integer.parseInt(value);
            if (type == long.class || type == Long.class) return Long.parseLong(value);
            if (type == float.class || type == Float.class) return Float.parseFloat(value);
            if (type == double.class || type == Double.class) return Double.parseDouble(value);
            if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
            if (type == char.class || type == Character.class) return value.charAt(0);

            // String
            if (type == String.class) return value;

            // BigDecimal / BigInteger
            if (type == BigDecimal.class) return new BigDecimal(value);
            if (type == BigInteger.class) return new BigInteger(value);

            // Date / Time types
            if (type == LocalDate.class)
                return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            if (type == LocalTime.class)
                return LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
            if (type == LocalDateTime.class) {
                try {
                    return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (DateTimeParseException e) {
                    return LocalDateTime.parse(value.replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }
            }

            if (type == OffsetDateTime.class)
                return OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            if (type == OffsetTime.class)
                return OffsetTime.parse(value, DateTimeFormatter.ISO_OFFSET_TIME);
            if (type == Instant.class)
                return Instant.parse(value);
            if (type == java.util.Date.class) {
                try {
                    return Date.from(Instant.parse(value));
                } catch (Exception e) {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                    } catch (ParseException ignore) {
                        return null;
                    }
                }
            }

            if (type == Calendar.class) {
                try {
                    Instant instant = Instant.parse(value.endsWith("Z") ? value : value + "Z");
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    calendar.setTime(Date.from(instant));
                    return calendar;
                } catch (Exception e) {
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        return calendar;
                    } catch (ParseException ignore) {
                        return null;
                    }
                }
            }

            if (type == java.sql.Date.class)
                return java.sql.Date.valueOf(value.split("T")[0]);

            if (type == java.sql.Time.class)
                return java.sql.Time.valueOf(value);

            if (type == java.sql.Timestamp.class)
                return java.sql.Timestamp.valueOf(value.replace("T", " "));



            // Enum
            if (type.isEnum()) {
                Class<? extends Enum> enumType = (Class<? extends Enum>) type;
                return Enum.valueOf(enumType, value);
            }

            if (type == byte[].class) {
                int length = value.length();
                byte[] bytes = new byte[length];

                for (int i = 0; i < length; i++) {
                    bytes[i] = (byte) value.charAt(i);
                }
                return bytes;
            }

            if (type == char[].class) {
                int length = value.length();
                char[] chars = new char[length];

                for (int i = 0; i < length; i++) {
                    chars[i] = value.charAt(i);
                }
                return chars;
            }

            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
