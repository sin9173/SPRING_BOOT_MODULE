package com.util.modules.aspect;

import com.util.modules.responsevo.ResponseVO;
import com.util.modules.utils.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(value = 2)
@Component
public class NullCheckAspect {

    @Around("execution(public * com.util.modules.controller.*.*(..))")
    public Object nullCheck(ProceedingJoinPoint pjp) throws Throwable {
        List<String> fail_list = new ArrayList<>();
        List<Object> check_list = new ArrayList<>();

        try {
            Object[] values = pjp.getArgs();

            //Args로 부터 Object를 가져옴
            if(values[0].getClass().getTypeName().equals("com.util.modules.requestvo.List_RequestVO")) {
                check_list.addAll((ArrayList<Object>)values[0].getClass().getMethod("getList").invoke(values[0]));
            } else {
                check_list.add(values[0]);
            }

            //Object Null 체크
            for(Object o : check_list) {
                if(RequestUtil.isParamNull(o)) fail_list.add(o.toString());
            }

            if(fail_list.size()>0) return new ResponseVO("10", "입력값 누락");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pjp.proceed();
    }

}
