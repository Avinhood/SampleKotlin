package com.cts.avin.di.modules


import android.app.Application
import android.content.Context
import android.net.NetworkInfo
import android.util.Log

import com.cts.avin.network.ApiService
import com.cts.avin.util.Constant
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.File
import java.util.concurrent.TimeUnit

import javax.inject.Inject
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import androidx.constraintlayout.widget.Constraints.TAG


@Module(includes = [FeatureModule::class])
class AppModule {

    val HEADER_CACHE_CONTROL = "Cache-Control"
    val HEADER_PRAGMA = "Pragma"
    @Inject
    internal var mContext: Context? = null

    private val isConnected: Boolean
        get() {
            try {
                val e = mContext!!.getSystemService(
                        Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
                val activeNetwork = e.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            } catch (e: Exception) {
                Log.w(TAG, "=====error network=======$e")
            }

            return false
        }

    @Provides
    internal fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    internal fun provideOkhttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .addInterceptor(interceptor)
        return client.build()
    }

    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }

    @Provides
    internal fun provideInterceptor(): Interceptor {
        return { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val cacheControl: CacheControl
            // removing any existing cache control definition
            requestBuilder.addHeader("Accept", "application/json;version=10")
                    .addHeader("Content-Type", "application/json")
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)

            if (isConnected) {
                cacheControl = CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build()
            } else {
                cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
            }
            // adding custom cache control definition
            requestBuilder
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    companion object {

        @Provides
        internal fun provideRetrofitService(retrofit: Retrofit): ApiService.ApiInterface {
            return retrofit.create(ApiService.ApiInterface::class.java!!)
        }
    }

}
