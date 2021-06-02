package com.moschip.services.helper

import com.moschip.services.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SpaceXFanClient {
    companion object {
        private var retrofit: Retrofit? = null

        private fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(provideHttpClient())
                    .build()
            }
            return retrofit!!
        }

        fun getApi(): SpaceXFanEndPoints {
            if (retrofit == null) {
                retrofit = getRetrofitInstance()
            }
            return retrofit!!.create(SpaceXFanEndPoints::class.java)
        }

        private fun provideHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.HTTP_LOG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val okhttpClientBuilder = OkHttpClient.Builder()
            okhttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            okhttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            okhttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
            okhttpClientBuilder.addInterceptor(loggingInterceptor)
            okhttpClientBuilder.addInterceptor(NetworkConnectionInterceptor())
            return okhttpClientBuilder.build()
        }

    }

}