package com.map.a2_2_teamproject.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 여기는 레트로핏 통신을 하기 위한 기본 세팅
public class ApiClient {

    private static final String BASE_URL = "http://1f75481b39e9.ngrok.io/"; //카카오와 하려면 여기 바꾸기
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //데이터 파싱 설정
                    .addConverterFactory(GsonConverterFactory.create())
                    //객체정보 반환
                    .build();
        }
        return  retrofit;
    }

}

