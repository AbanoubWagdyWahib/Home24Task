package com.home24task.DataLayer.Retrofit;

import java.util.List;

public class RemoteDataSourceUsingRetrofit {

    private static RemoteDataSourceUsingRetrofit INSTANCE;

    private RemoteDataSourceUsingRetrofit() {
    }

    public static RemoteDataSourceUsingRetrofit getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSourceUsingRetrofit();
        return INSTANCE;
    }
}