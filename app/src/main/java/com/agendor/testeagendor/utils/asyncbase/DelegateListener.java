package com.agendor.testeagendor.utils.asyncbase;

/**
 * Delegate, listener responsável pela execução de métodos que serão implementados na criação do objeto AsyncTaskBase
 * @author Robson Freitas
 * @param <T>
 */
public interface DelegateListener<T> {
    public void onPosExecute(ResponseData<T> response);
}
