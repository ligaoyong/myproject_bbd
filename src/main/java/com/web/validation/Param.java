package com.web.validation;

import lombok.Data;
import org.checkerframework.common.value.qual.StringVal;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class Param {
    @NotNull(message = "param1必须不为空")
    String param1;

    @Min(value = 100,message = "param2必须大于100")
    Integer param2;

    @Max(value = 300,message = "param3必须小于300")
    BigDecimal param3;

    @Length(min = 5,max = 10,message = "长度必须5-10")
    String param4;

    @IdentityCardNumber
    String param5;
}
