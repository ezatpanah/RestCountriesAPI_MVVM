package com.ezatpanah.restcountriesaapi_mvvm.di


import com.ezatpanah.restcountriesaapi_mvvm.api.ApiServices
import com.ezatpanah.restcountriesaapi_mvvm.utils.Constants.BASE_URL
import com.ezatpanah.restcountriesaapi_mvvm.utils.Constants.NAMED_BODY
import com.ezatpanah.restcountriesaapi_mvvm.utils.Constants.NAMED_HEADER
import com.ezatpanah.restcountriesaapi_mvvm.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideNetworkTimeout() = NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    @Named(NAMED_HEADER)
    fun provideHeaderInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    @Named(NAMED_BODY)
    fun provideBodyInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(
        time: Long,
        @Named(NAMED_HEADER) header: HttpLoggingInterceptor,
        @Named(NAMED_BODY) body: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(header)
        .addInterceptor(body)
        .connectTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .writeTimeout(time, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiServices::class.java)

}