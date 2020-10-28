

package com.baizhi.component;

import com.baizhi.entity.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SelectException.class)
    public Response bizException(SelectException e){
        return new Response(500,e.getMessage(),null);
    }




    @ExceptionHandler(Exception.class)//针对这个异常进行捕获
    public Response exceptionHandler(Exception e){
        e.printStackTrace();
        return new Response(500,"发生了异常",null);
    }


}
