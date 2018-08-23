package com.imerchantech.bakinapp.utils.mvp;

public interface LoadCallback<T> {

    void onSuccess(T response);

    void onFailure(Throwable throwable);
}
