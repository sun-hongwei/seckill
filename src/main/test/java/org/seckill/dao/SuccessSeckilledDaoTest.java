package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessSeckilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sun on 2017/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessSeckilledDaoTest {

    @Autowired
    private SuccessSeckilledDao successSeckilledDao;

    @Test
    public void insertSucessKilled() throws Exception {
        int i = successSeckilledDao.insertSucessKilled(1000, 15590025521l);
        System.out.println(i);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessSeckilled successSeckilled = successSeckilledDao.queryByIdWithSeckill(1000,15590025520l);
        System.out.println(successSeckilled);
        System.out.println(successSeckilled.getSeckill().getName());
    }

}