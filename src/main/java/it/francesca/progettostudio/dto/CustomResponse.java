package it.francesca.progettostudio.dto;

import lombok.Data;
@Data
public class CustomResponse<T> {
    private T payload;
    private String error;
    private int httpStatusCode;

    public CustomResponse(T payload, int httpStatusCode){
        this.payload = payload;
        this.httpStatusCode = httpStatusCode;
    }

    public CustomResponse(String error, int httpStatusCode){
        this.error = error;
        this.httpStatusCode = httpStatusCode;
    }


}
