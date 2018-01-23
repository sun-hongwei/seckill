package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sun on 2017/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现依赖
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNuumber() throws Exception {
        Date date = new Date();
        int i = seckillDao.reduceNuumber(1000, date);
        System.out.println(i);
    }

    /**
     * @throws Exception
     */
    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void qeryAll() throws Exception {
        List<Seckill> seckills = seckillDao.qeryAll(0, 10);
        for (Seckill seckill : seckills) {
            System.out.println(seckill.getSeckillId());
        }
    }

}