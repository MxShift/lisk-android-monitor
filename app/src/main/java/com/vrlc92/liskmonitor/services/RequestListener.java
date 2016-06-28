package com.vrlc92.liskmonitor.services;

/**
 * Created by victorlins on 4/15/16.
 */
public interface RequestListener<T> {
    void onFailure(Exception e);
    void onResponse(T object);
}
