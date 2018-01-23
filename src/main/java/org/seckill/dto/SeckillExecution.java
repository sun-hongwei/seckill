package org.seckill.dto;

import org.seckill.entity.SuccessSeckilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * Created by sun on 2017/11/6.
 * 秒杀执行后结果
 */
public class SeckillExecution {

    private long seckillId;

    private int state;

    private String stateInfo;

    private SuccessSeckilled successSeckilled;

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessSeckilled successSeckilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successSeckilled = successSeckilled;
    }

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSeckilled getSuccessSeckilled() {
        return successSeckilled;
    }

    public void setSuccessSeckilled(SuccessSeckilled successSeckilled) {
        this.successSeckilled = successSeckilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successSeckilled=" + successSeckilled +
                '}';
    }
}
