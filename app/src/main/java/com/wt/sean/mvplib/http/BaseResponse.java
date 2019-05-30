package com.wt.sean.mvplib.http;

public class BaseResponse<T>  {

    private int result; // 返回的code
    private T data; // 具体的数据结果
    private String message; // message 可用来返回接口的说明

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
