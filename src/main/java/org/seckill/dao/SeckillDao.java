package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by sun on 2017/11/4.
 */
public interface SeckillDao {

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNuumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 查询商品具体信息
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 查询所有的秒杀商品
     *
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> qeryAll(@Param("offet") int offet, @Param("limit") int limit);

}
