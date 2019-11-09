package kz.smart.calendar.di.modules

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.Gson
import okhttp3.OkHttpClient
import dagger.Provides
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import kz.smart.calendar.Constants
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.di.CustomApplicationScope


@Module(includes = [NetworkModule::class])
class ServiceUtilModule {
    @Provides
    @CustomApplicationScope
    fun getGson(): Gson {
        val gsonBuilder = GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm")
        return gsonBuilder.create()
    }

    @Provides
    @CustomApplicationScope
    fun getServiceUtil(retrofit: Retrofit): ApiManager {
        return retrofit.create(ApiManager::class.java)
    }

    @Provides
    @CustomApplicationScope
    fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.apiEndpoint)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}