package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessSeckilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessSeckilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by sun on 2017/11/6.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessSeckilledDao successSeckilledDao;

    private final String slat = "ashdfjklhasjklfhasklhjdfhjkl**&^&*^%^*%*%^&%^*&^4535KLKJLKJ";

    public List<Seckill> getSeckillList() {
        return seckillDao.qeryAll(0, 4);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //没有对应的秒杀产品
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        java.util.Date date = new java.util.Date();
        if (date.getTime() < startTime.getTime() || date.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, date.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    //声名式事务
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("数据重写");
        }
        java.util.Date date = new java.util.Date();
        try {
            int updataCount = seckillDao.reduceNuumber(seckillId, date);
            if (updataCount <= 0) {
                throw new SeckillCloseException("秒杀结束");
            } else {
                int insertConut = successSeckilledDao.insertSucessKilled(seckillId, userPhone);
                if (insertConut <= 0) {
                    throw new RepeatKillException("重复进行秒杀，应该把你加入黑名单");
                } else {
                    SuccessSeckilled successSeckilled = successSeckilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successSeckilled);
                }
            }
        } catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new SeckillException("内部错误"+e.getMessage());
        }
    }

    /**
     * 生成Md5
     *
     * @param seckillId
     * @return
     */
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
