package kz.smart.calendar.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kz.smart.calendar.Constants
import kz.smart.calendar.api.TokenInterceptor
import kz.smart.calendar.di.ApplicationContext
import kz.smart.calendar.di.CustomApplicationScope
import kz.smart.calendar.models.shared.DataHolder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = [ApplicationModule::class])
class NetworkModule {

    @Provides
    @CustomApplicationScope
    fun getInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
                message -> Timber.v(message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @CustomApplicationScope
    fun getSessionInterceptor(): JwtInterceptor {
        val interceptor = JwtInterceptor()
        return interceptor
    }

    @Provides
    @CustomApplicationScope
    fun getCache(cacheFile: File): Cache {
        return Cache(cacheFile, 100 * 1000 * 1000)
    }

    @Provides
    @CustomApplicationScope
    fun getFile(@ApplicationContext context: Context): File {
        val file = File(context.filesDir, "cache_dir")
        if (!file.exists())
            file.mkdirs()
        return file
    }

    @Provides
    @CustomApplicationScope
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor, jwtInterceptor: JwtInterceptor, cache: Cache, tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(Constants.writeTimeout, TimeUnit.SECONDS)
            .readTimeout(Constants.readTimeout, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(jwtInterceptor)
            .build()
    }
}

class JwtInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header("No-Authentication")==null){
            val token = DataHolder.sessionId

            request = if(token != null && token.isNotEmpty()) {
                request.newBuilder()
                    .addHeader("X-SESSION-ID", token)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-ACCEPT-VERSION", Constants.version)
                    .build()
            } else {
                request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-ACCEPT-VERSION", Constants.version)
                    .build()
            }
        }
        return chain.proceed(request)
    }

}
