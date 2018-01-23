package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessSeckilled;

/**
 * Created by sun on 2017/11/4.
 * 成功秒杀商品接口
 */
public interface SuccessSeckilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param SeckillId
     * @param userPhone
     * @return
     */
    int insertSucessKilled(@Param("SeckillId") long SeckillId, @Param("userPhone") long userPhone);

    /**
     * 根据SeckillId查询秒杀实体
     * @param SeckillId
     * @return
     */
    SuccessSeckilled queryByIdWithSeckill(@Param("SeckillId") long SeckillId, @Param("userPhone") long userPhone);
}
