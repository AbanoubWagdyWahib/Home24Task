package com.home24task.DataLayer;

import android.content.Context;
import com.home24task.DataLayer.Retrofit.RemoteDataSourceUsingRetrofit;

public class DataRepository implements RepositorySource {

    private final String TAG = getClass().getSimpleName();

    private static DataRepository INSTANCE;

    private RemoteDataSourceUsingRetrofit mRetrofitService;

    private DataRepository(Context context) {
        mRetrofitService = RemoteDataSourceUsingRetrofit.getInstance();
    }

    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(context);
        }
        return INSTANCE;
    }
}