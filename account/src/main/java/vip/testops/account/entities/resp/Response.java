package vip.testops.account.entities.resp;

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
        this.code = 1001;
        this.message = "success";
        this.data = data;
    }
}
