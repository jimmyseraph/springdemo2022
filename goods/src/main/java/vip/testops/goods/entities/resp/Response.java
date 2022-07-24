package vip.testops.goods.entities.resp;

import lombok.Data;

@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public void missingParam(String paramName) {
        this.code = 2001;
        this.message = "missing param {"+paramName+"}";
    }

    public void internalError(String reason) {
        this.code = 5001;
        this.message = "internal error: {"+reason+"}";
    }

    public void successWithData(T data) {
        this.code = 1000;
        this.message = "success";
        this.data = data;
    }

    public void commonSuccess() {
        this.code = 1000;
        this.message = "success";
    }

    public void invalidToken() {
        this.code = 2002;
        this.message = "invalid token";
    }

    public void invalidNameOrPassword() {
        this.code = 2003;
        this.message = "invalid name/password";
    }
}
