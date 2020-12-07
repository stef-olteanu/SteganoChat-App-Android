package com.stefanolteanu.steganochatapp.Network


//import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.stefanolteanu.steganochatapp.Utils.GlobalApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface BaseAPI {

    companion object {
        private const val BASE_URL = "http://192.168.100.187:8080/api/"
        private var retrofit: Retrofit? = null

        operator fun invoke(): Retrofit {
            if(retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()
            }
            return retrofit!!
        }


        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(ChuckerInterceptor.Builder(GlobalApplication.getApplicationContext())
                        .collector(ChuckerCollector(GlobalApplication.getApplicationContext()))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build())
                .build()
        }
    }
}