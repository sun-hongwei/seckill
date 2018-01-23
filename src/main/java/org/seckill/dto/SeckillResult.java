package org.seckill.dto;

/**
 * 所有ajax请求返回类型，封装json结果
 * Created by sun on 2017/11/7.
 */
public class SeckillResult<T> {
    private boolean sucess;
    private T data;
    private String error;

    public SeckillResult(boolean sucess, T data) {
        this.sucess = sucess;
        this.data = data;
    }

    public SeckillResult(boolean sucess, String error) {
        this.sucess = sucess;
        this.error = error;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
