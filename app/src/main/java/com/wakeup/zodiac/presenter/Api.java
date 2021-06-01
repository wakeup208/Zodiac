package com.wakeup.zodiac.presenter;



import com.wakeup.zodiac.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("{path}")
    Call<Response> getmyjson(@Path("path") String str);
}
