package com.hmdp.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.hmdp.dto.UserDTO;
import com.hmdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * Created by 杜鹏豪 on 2022/11/29.
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取tokenKey
        String tokenKey = request.getHeader("authorization");

        //2.从redis中查询数据
        if(tokenKey!=null){
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(tokenKey);

            if (!entries.isEmpty()) {

                UserDTO userDTO = BeanUtil.fillBeanWithMap(entries, new UserDTO(), false);

                stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

                //4.存入ThreadLocal给后续接口使用
                UserHolder.saveUser(userDTO);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求完毕,移除用户避免内存泄露
        UserHolder.removeUser();
    }
}
