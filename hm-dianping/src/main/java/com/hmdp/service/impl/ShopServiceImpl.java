package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_KEY;
import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TTL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Shop queryShopById(Long id) {
        Shop shop;
        //1.生成key
        String shopKey = CACHE_SHOP_KEY + id;
        //2.查询缓存
        String json = stringRedisTemplate.opsForValue().get(shopKey);
        if (json == null || json.isEmpty()) {
            //未查到缓存
            //查数据库
            shop = query().eq("id", id).one();
            //设入缓存并设置超时事件
            stringRedisTemplate.opsForValue().set(shopKey, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        } else {
            //查询到缓存
            shop = JSONUtil.toBean(json, Shop.class, false);
        }
        //返回
        return shop;
    }

    @Override
    public List<Shop> queryShopByType(Integer typeId, Integer current) {
        //存json?不好 这样更新很麻烦  好的是list并且用排序的
        //key: cache:shop:type:typeId:page:current      value:List



        Page<Shop> page = query()
                .eq("type_id", typeId)
                .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
        // 返回数据
        List<Shop> shopList = page.getRecords();

        return null;
    }
}
