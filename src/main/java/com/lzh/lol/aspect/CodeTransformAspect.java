package com.lzh.lol.aspect;

import com.lzh.lol.dto.UserDTO;
import com.lzh.lol.po.User;
import com.lzh.lol.service.OpenIdService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @ClassName CodeTransform
 * @Description TODO
 * @Author lzh
 * @Date 2021/7/27 18:47
 */
@Aspect
@Component
/**
 *
 * 前端->openId(wx小程序唯一标识)->后端
 * 切面-openid->userId
 *
 *
 * User user=new User();
 *
 * ioc
 * User -> Bean ->容器
 * @Autowaied @Resource ->User
 * 控制权 -》 ioc
 *
 * aop
 * |
 * |
 * |
 *
 * ---------------------------
 *
 */
public class CodeTransformAspect {

    Logger logger = LoggerFactory.getLogger(CodeTransformAspect.class);

    @Resource
    private OpenIdService openIdService;

    //定义切点
    //private final String POINT_CUT = "execution(public * com.lzh.lol.controller.*.*(..))";
    private final String POINT_CUT = "@annotation(com.lzh.lol.annotation.CodeTransformAnno)";

    @Pointcut(POINT_CUT)
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pc) throws Throwable {
        logger.info("@Around环绕通知执行:{}", pc.getSignature().toString());
        MethodSignature signature = (MethodSignature) pc.getSignature();
        //获取切入方法的对象
        Method method = signature.getMethod();
        //获取参数值
        Object[] args = pc.getArgs();
        //logger.info(String.valueOf(args.length));
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].contains("user")) {
                args[i] = transformCode(args[i]);
            }
            logger.info(parameterNames[i]);
        }
        Object obj = null;

        obj = pc.proceed(args);
        logger.info(obj.toString());

        logger.info("@Around环绕通知执行结束.....");
        return obj;
    }

    private Object transformCode(Object arg) throws Exception {
        User user = (User) arg;
        user.setOpenid(openIdService.getOpenId(user.getOpenid()));
        return user;
    }
}
