package com.m77777_888.myapplication.screens.webview_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.m77777_888.myapplication.R
import kotlinx.android.synthetic.main.fragment_web_view.view.*

var referenceBuffer: String? = null
const val TAG = "TTT"

class WebViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)

        view.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                try {
                    val cookie = android.webkit.CookieManager.getInstance().getCookie(url)
                    Log.e(TAG, "For URL: $url \n [$cookie]")
                    toast("Cookie for URL: $url \n [$cookie]")
                } catch (e: Throwable) {
                    Log.e(TAG, "Android WebKitCookieManager error \n $e")
                    toast("Android WebKitCookieManager error \n $e")
                }
            }
        }
        referenceBuffer?.let { view.webView.loadUrl(referenceBuffer!!) }

        return view
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}