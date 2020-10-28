package com.baizhi.component;

import com.baizhi.entity.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Aspect
@Component
public class ParamCheckAop {

    @Around("execution(public * com.baizhi.controller..*(..)) && args(..,bindingResult)")//代表我这个Around是针对pointcut()这个切点
    public Response checkRole(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
        if (bindingResult.hasErrors()){//如果有错误打印错误信息
            String errorMsg="";
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMsg +=fieldError.getField()+fieldError.getDefaultMessage();
                errorMsg +=" ";
            }
            Response response=new Response();
            response.setCode(400);
            response.setMsg(errorMsg);
            return response;
        }else {
            return (Response) joinPoint.proceed();//没有错误就放行
        }

    }


}
