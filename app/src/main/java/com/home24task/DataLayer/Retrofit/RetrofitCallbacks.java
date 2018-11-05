package com.home24task.DataLayer.Retrofit;

import org.json.JSONObject;
import retrofit2.Call;

import java.util.List;

public interface RetrofitCallbacks {

    interface BaseNetworkCallbacks<OnSuccessReturnType> {
        void onSuccess(OnSuccessReturnType result);

        void onFailure(Call<OnSuccessReturnType> call, Throwable t);
    }


    interface ChangeLanguageCallback {
        void onChange(Boolean onchange);
    }
}
