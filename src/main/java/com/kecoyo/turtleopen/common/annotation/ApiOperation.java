package com.kecoyo.turtleopen.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Inherited
@Documented
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Operation(security = @SecurityRequirement(name = "authScheme"))
public @interface ApiOperation {
    String method() default "";

    String[] tags() default {};

    String summary() default "";

    String description() default "";

    RequestBody requestBody() default @RequestBody;

    Parameter[] parameters() default {};

    ApiResponse[] responses() default {};

    boolean hidden() default false;

}
