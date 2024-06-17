package me.qunqun.doctor.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Result<T> {
    private int code;
    private String message;
    private T data;
    public static <T> Result<T> success(T data){
        Result<T> result=new Result<T>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(String message){
        Result<T> result=new Result<T>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
    public static <T> Result<T> error(int code,String message){
        Result<T> result=new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    public static <T> Result<T> error(String message, Exception e){
        log.error(message, e);
        Result<T> result=new Result<T>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }


    public boolean isError() {
        return code != 200;
    }
}
