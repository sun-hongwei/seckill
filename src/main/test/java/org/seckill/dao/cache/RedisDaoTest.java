package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sun on 2017/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;

    private long seckillId = 1001;

    @Test
    public void testSeckill() throws Exception {
        Seckill secckill = redisDao.getSecckill(seckillId);
        if (secckill == null) {
            Seckill seckill = seckillDao.queryById(seckillId);
            if (seckill != null) {
                String s = redisDao.putSeckill(seckill);
                System.out.println(s);
            }
        } else {
            Seckill redisDaoSecckill = redisDao.getSecckill(seckillId);
            System.out.println(redisDaoSecckill);
        }
    }

}