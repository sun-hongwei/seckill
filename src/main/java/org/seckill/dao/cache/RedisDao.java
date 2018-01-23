package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by sun on 2017/11/9.
 */
public class RedisDao {

    private JedisPool jedisPool;

    public RedisDao(String ip, int porl) {
        jedisPool = new JedisPool(ip, porl);
    }

    private RuntimeSchema<Seckill> seckillRuntimeSchema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSecckill(long seckillId) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "seckill:" + seckillId;
            //并没有实现内部序列化操作
            //get->byte[]->反序列化->Object(seckill)
            //采用自定义序列化
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null) {
                //创建一个空对象
                Seckill seckill = seckillRuntimeSchema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, seckillRuntimeSchema);
                //反序列化 将bytes 序列化成seckill
                return seckill;
            }
        } finally {
            jedis.close();
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, seckillRuntimeSchema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            int timeout = 60 * 60;
            String setex = jedis.setex(key.getBytes(), timeout, bytes);
            return setex;
        } finally {
            jedis.close();
        }
    }

}
