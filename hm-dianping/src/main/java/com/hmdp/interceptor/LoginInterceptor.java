package com.hmdp.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.hmdp.dto.UserDTO;
import com.hmdp.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;
import static com.sun.xml.internal.ws.util.JAXWSUtils.getUUID;

/**
 * Created by 杜鹏豪 on 2022/11/29.
 */
public class LoginInterceptor implements HandlerInterceptor {



    public LoginInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.判断是否有用户
        UserDTO userDTO = UserHolder.getUser();
        if(userDTO==null){
            response.setStatus(HttpStatus.HTTP_FORBIDDEN);
            return false;
        }

        return true;
    }

}
