package com.agendor.testeagendor.utils.asyncbase;

import android.content.Context;
import android.os.AsyncTask;

/**
 * AysncTaskBase, classe base das asyncs criadas.
 * @author Robson Freitas
 * @param <T> Parâmetro que o doInBackground irá receber.
 * @param <V> Parâmetro que o objeto ResponseData do método onPostExecute(ResponseData<V>) irá receber e que o doInBackground irá retornar.
 * @param <U> Parâmetro que o onProgressUpdate irá receber.
 */
public abstract class AsyncTaskBase<T, U, V> extends AsyncTask<T, U, ResponseData<V>> {

    private DelegateListener<V> delegate;
    private Context context;

    protected AsyncTaskBase(Context context, DelegateListener<V> delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    protected AsyncTaskBase(Context context) {
        this(context, null);
    }

    protected Context getCurrentContext(){
        return context;
    }

    @Override
    protected void onPostExecute(ResponseData<V> response) {
        if(delegate != null){
            this.delegate.onPosExecute(response);
        }
    }
}
