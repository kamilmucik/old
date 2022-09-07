package pl.estrix.app.backend.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * column position in query. Starts by 0
     *
     * @return Column position in query.
     */
    int index() default 0;

    /**
     * column optional in query.
     *
     * @return false if not defined.
     */
    boolean optional() default false;

    /**
     * Informs NativeQueryResultSetHandler that column should be
     * parsed by custom pattern. When set to not blank value NativeQueryResultSetHandler
     * uses DateTimeFormatter.ofPattern to convert field.
     * localDateTimeFormat() works only with LocalDateTime
     * and has no effect on other types.
     *
     * @return LocalDateTime format pattern
     */
    String localDateTimeFormat() default "";

    /**
     * Informs NativeQueryResultSetHandler that column should be
     * parsed as Timestamp. When set to true NativeQueryResultSetHandler
     * uses AuroraDateHelper.convertFromTrimmedMilliseconds to convert field.
     * localDateTimeTimestamp() works only with LocalDateTime
     * and has no effect on other types.
     *
     * @return Is column should be parsed as Timestamp
     */
    boolean localDateTimeTimestamp() default false;
}
