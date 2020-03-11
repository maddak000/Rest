package com.maddakbasement.android.dbconnectionrest

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maddakbasement.android.dbconnectionrest.dbinterface.RestService
import com.maddakbasement.android.dbconnectionrest.dbinterface.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    var text: String = ""
//    val c = getConnect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button.setOnClickListener{
        getProduct()}
    }

    private val service by lazy {
        val factory = ServiceFactory.getInstance("https://rdb-simpledb.restdb.io/rest")
        factory.build(RestService::class.java)
    }
    private var disposable: Disposable? = null


    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun getProduct() {

        this.disposable = this.service.readCompany()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ player ->
                run {
                    showResult(player.name)
                }
            },
                { showResult("Failed to read the product") })
    }
}


//            val test = getConnect()
//            test.execute()
//            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
//        }
//    }

//    fun connect(): String {
//        val connection: HttpsURLConnection = URL("https://rdb-simpledb.restdb.io/rest/product/5662d2d763270072000000df").openConnection() as HttpsURLConnection
//        connection.setRequestProperty("User-Agent", "my-restdb-app");
//        connection.setRequestProperty("Accept", "application/json");
//        connection.setRequestProperty("x-apikey", "560bd47058e7ab1b2648f4e7");
//
//
//        if (connection.getResponseCode() == 200) {
//            connection.connect();
//            val text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
//            return text
//        } else {
//            return "ERROR"
//        }
//    }
//
//    inner class getConnect: AsyncTask<String,Int,String>(){
//        override fun doInBackground(vararg params: String?): String {
//            val c = connect()
//            text = c
//            return c
//        }
//
//        override fun onPostExecute(c: String?) {
//            super.onPostExecute(c)
//            c.toString()
//        }
//    }

//Toast.makeText(this, "THE ONCREATE METHOD IS CALLED", Toast.LENGTH_SHORT).show()


