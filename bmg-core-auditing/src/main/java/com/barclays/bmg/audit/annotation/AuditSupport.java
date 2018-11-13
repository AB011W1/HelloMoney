package com.barclays.bmg.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AuditSupport {
    String auditType() default "";

    String activityState();

    String stepId() default "";

    boolean mapEnabled() default true;

    String resolveFlag() default "";

    String activityId() default "";

    String serviceDescription() default "";

    String activityType();
}
