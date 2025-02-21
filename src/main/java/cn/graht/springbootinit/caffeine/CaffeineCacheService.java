package cn.graht.springbootinit.caffeine;

import cn.graht.springbootinit.constant.CaffeineKeyConstant;
import cn.graht.springbootinit.model.vo.UserVO;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author GRAHT
 */

@Getter
public class CaffeineCacheService {

    private final Cache<String, UserVO> userCache;

    public CaffeineCacheService(){
        this.userCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS) // 设置缓存过期时间
                .maximumSize(10000) // 设置最大缓存条目数
                .build();
    }
    public void putUserCache(String key, UserVO value){
        userCache.put(CaffeineKeyConstant.USER_CACHE_KEY+key, value);
    }

    public UserVO getUserCache(String key){
        return userCache.getIfPresent(CaffeineKeyConstant.USER_CACHE_KEY+key);
    }
    public void removeUserCache(String key){
        userCache.invalidate(CaffeineKeyConstant.USER_CACHE_KEY+key);
    }
    public void removeUserCacheByUser(UserVO userVo){
        userCache.invalidate(CaffeineKeyConstant.USER_CACHE_KEY+Objects.requireNonNull(userVo.getId()));
    }
}
