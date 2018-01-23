package org.seckill.enums;

/**
 * Created by sun on 2017/11/6.
 */
public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INSERT_ERROR(-2,"系统异常");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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

    public static SeckillStateEnum stateof(int index){
        for (SeckillStateEnum seckillStateEnum : values()) {
            if(seckillStateEnum.getState() == index){
                return seckillStateEnum;
            }
        }
        return null;
    }

}
