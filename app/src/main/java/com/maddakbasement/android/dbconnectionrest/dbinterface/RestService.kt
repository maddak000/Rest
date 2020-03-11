package com.maddakbasement.android.dbconnectionrest.dbinterface

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface RestService {
    @GET("people/5e69342133a9df6c0000f386")
    fun read(): Observable<ModelPerson.Person>

    @GET("product/{_id}")
    fun readProduct(@Path("_id") _id: String): Observable<ModelProduct.Product>

    @GET("companies/5631d356f941f47900000110")
    fun readCompany(): Observable<ModelCompany.Company>
}

//@Query("x-apikey") apikey: String

object ModelPerson {
    data class Person(
//        val _id: String,
        val name: String,
        val id: String
    )
}


object ModelProduct {
    data class Product(
//        val _id: String,
        val name: String
 //       val photos: List<String>,
//        val serialno: Int,
 //       val price: Int,
//        val _mock: Boolean
    )
}

object ModelPlayer {
    data class Player(
        val _id: String,
        val name: String,
        val email: String,
        val score: Int,
        val category: String,
        val team: String,
        val registered: String,
        val _mock: Boolean
    )
}

object ModelCompany {
    data class Company(
 //       val _id: String,
        val name: String
 //       val address: String,
//        val city: String,
//        val zip: String
    )
}

class ServiceFactory private constructor(private val retrofit: Retrofit) {

    companion object {
        @Volatile
        private var INSTANCE: ServiceFactory? = null

        /**
         * Returns a singleton ServiceFactory.
         */
        fun getInstance(baseUrl: String): ServiceFactory =
            INSTANCE ?: synchronized(this) {

                INSTANCE ?: build(baseUrl).also { INSTANCE = it }
            }

        /**
         * Build a ServiceFactory object.
         */

        val clientBuilder = OkHttpClient.Builder()
        val headerAuthorizationInterceptor = Interceptor { chain ->
            var request = chain.request()
            val headers = request.headers()
                .newBuilder()
//                .add("Content-Type", "application/json")
                .add("x-apikey", "560bd47058e7ab1b2648f4e7")
                .build()
            request = request.newBuilder().headers(headers).build()
            chain.proceed(request)
        }
        val okHttpClient = clientBuilder.addInterceptor(headerAuthorizationInterceptor)
            .build()

        private fun build(baseUrl: String): ServiceFactory {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
            return ServiceFactory(retrofit)
        }

    }

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }
}