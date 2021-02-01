package com.agendor.testeagendor.utils.asyncbase;

import java.io.Serializable;

/**
 * Objeto de resposta genérico para AsyncTask
 * @author Robson Freitas
 * @param <T>
 */
public class ResponseData<T> implements Serializable {

    private T data;
    private String message;
    private boolean successfully;

    /**
     * Serialização
     */
    private static final long serialVersionUID = -1396907768012910102L;

    public ResponseData(boolean successfully, String message, T data) {
        this.successfully = successfully;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccessfully() {
        return successfully;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
