package com.baizhi.component;



import com.baizhi.annotation.NeedRole;
import com.baizhi.entity.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class RoleIdAop {

    //切点用point这个注解，execution()表示在执行的过程中，public代表公共的，*任何返回值类型的，com.baizhi.controller.*代表controller里面所有的方法。(..)代表所有的参数。
    //固定写法多层目录打两个..*
    @Pointcut("execution(public * com.baizhi.controller..*(..))")
    public void pointcut(){
    }

    @Around("pointcut()")//代表我这个Around是针对pointcut()这个切点
    public Response checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        //这个参数是固定的代表切点
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();//拿到签名,然后强转为方法签名
        Method method = signature.getMethod();//通过方法签名拿到方法，这个是为了去拿去做反射使用。
        NeedRole annotation = method.getAnnotation(NeedRole.class);//拿到注解
        int[] values = annotation.value();//拿到注解里的值
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//拿到请求的上下文
        ServletRequestAttributes requestAttributes1=(ServletRequestAttributes) requestAttributes;//真实能拿到请求的是这个实现类，所以要做一个强转。
        HttpServletRequest request = requestAttributes1.getRequest();//拿到这个请求
        String roleId = request.getHeader("roleId");
        Integer integer = Integer.valueOf(roleId);
        for (int role:values){
            if (role==integer){
                return (Response) joinPoint.proceed();
            }
        }
        Response response=new Response(400,"你的权限不足",null);
        return response;
        }




    }



