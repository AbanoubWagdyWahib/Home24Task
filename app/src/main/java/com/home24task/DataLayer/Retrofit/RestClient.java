package com.home24task.DataLayer.Retrofit;

import com.home24task.App;
import com.home24task.Utils.GlobalKeys;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RestClient {

    private static RetrofitService retrofitService;

    private static final int MAX_STALE = 60 * 60 * 24 * 7;

    public static RetrofitService getRetrofitService() {
        if (retrofitService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    .addInterceptor(new OfflineResponseCacheInterceptor())
                    .cache(App.getNetworkCache())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalKeys.EndPoints.BaseURL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

            retrofitService = retrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }

    public static RetrofitService getRetrofitService(String url) {
        if (retrofitService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new ResponseCacheInterceptor())
                    .addInterceptor(new OfflineResponseCacheInterceptor())
                    .cache(App.getNetworkCache())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();

            retrofitService = retrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }

    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (chain.request().method().equals("GET")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=0")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
            } else {
                return originalResponse;
            }
        }
    }

    private static class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!App.isNetworkAvailable()) {
                if (request.method().equals("GET")) {
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE)
                            .build();
                }
            }
            return chain.proceed(request);
        }
    }
}