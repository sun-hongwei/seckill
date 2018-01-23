package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by sun on 2017/11/6.
 */
public interface SeckillService {

    /**
     * 获取所有秒杀记录
     * @return
     */
   List<Seckill> getSeckillList();

    /**
     * 通过Id获取秒杀
     * @param seckillId
     * @return
     */
   Seckill getSeckillById(long seckillId);

    /**
     * 输出开启秒杀接口地址，否则输出系统时间和秒杀时间
     * @param seckillId
     */
   Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
