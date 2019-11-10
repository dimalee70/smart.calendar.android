package kz.smart.calendar.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(): Interceptor{
    var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if(original.url().encodedPath().contains("/gettoken") &&
            original.method() == "post"
        )
            return chain.proceed(original)

        val originalHttpUrrl = original.url()
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", token)
            .addHeader("Content-Type", "application/json")
            .url(originalHttpUrrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}