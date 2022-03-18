package fr.fonkio.downloadstationviewer.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder(url : String) {

    private val okHttp = OkHttpClient.Builder()

    private val builder = Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService (serviceType : Class<T>) : T {
        return retrofit.create(serviceType)
    }
}