package com.m77777_888.myapplication

import android.app.Application
import android.util.Log
import com.m77777_888.myapplication.api.BaseAPI
import net.gotev.cookiestore.InMemoryCookieStore
import net.gotev.cookiestore.SharedPreferencesCookieStore
import net.gotev.cookiestore.WebKitSyncCookieManager
import net.gotev.cookiestore.okhttp.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

const val START_REFERENCE = "https://fairslot.ru/N8HzKYj2"
const val START_URL = "https://yandex.ru"
const val START_ENDPOINT = "/"
const val COOKIE_STORE_NAME = "a77777_888"
const val TAG = "TestGame"

class App: Application() {

    companion object {
        lateinit var cookieManager: WebKitSyncCookieManager
//        lateinit var cookieApi: BaseAPI
    }

    var workURL: String? = null

    override fun onCreate() {
        super.onCreate()

        cookieManager = WebKitSyncCookieManager(
            createCookieStore(COOKIE_STORE_NAME, true),
            CookiePolicy.ACCEPT_ALL
        ) {
            Log.e(TAG, "WebKitSyncCookieManager error \n $it")
        }


        CookieManager.setDefault(cookieManager)

    }

    private fun createCookieStore(name: String, persistent:Boolean) =
        if (persistent) {
            SharedPreferencesCookieStore(this, name)
        } else {
            InMemoryCookieStore(name)
        }

}