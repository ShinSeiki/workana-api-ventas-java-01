package com.calero.lili.api.annotations;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// se utiliza en findAllPaginateDetails ver controller
@ApiImplicitParams(
        value = {
                @ApiImplicitParam(
                        name = "numeroIdentificacion",
                        dataType = "string",
                        paramType = "query"
                )
        }
)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VtFacturasListFilter {
}
