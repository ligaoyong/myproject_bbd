package com.web.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultVO {
    String code;
    String message;
    Object data;
}
