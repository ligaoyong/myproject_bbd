package com.web.validation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ValidationController {

    @GetMapping("get1")
    public ResultVO get(@Validated Param param){ //@Valid注解也可以
        //参数校验失败会抛出BindException异常
        /**
         * 注：spring自带的参数校验不是使用AOP功能来实现的 而是在一开始的数据绑定中校验的
         *    我们也可以使用自定义的AOP(前置通知)来实现
         */
        return ResultVO.builder().code("200").data("ok").build();
    }

}
