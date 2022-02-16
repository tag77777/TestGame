package com.m77777_888.myapplication.screens.start_fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.m77777_888.myapplication.R
import com.m77777_888.myapplication.api.ReferenceRetrofitInstance
import com.m77777_888.myapplication.screens.webview_fragment.referenceBuffer
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private val scope = MainScope()
    private var reference: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scope.launch {
            setReference()
            navigate(view)
        }
    }

    private suspend fun setReference() {
        if (!isOnline(requireContext())) return

        val response = ReferenceRetrofitInstance.api.getReference()

        if (response.body() == null) {
//            Toast.makeText(requireContext(), "response body is NULL", Toast.LENGTH_SHORT).show()
            return
        } else {
            val _reference = response.body()!!
            reference = if (Patterns.WEB_URL.matcher(_reference).matches()) _reference else null
        }

    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private suspend fun navigate(view: View) {
        referenceBuffer = reference
        delay(3000)
        if (reference == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_gameFragment)
        } else {
            Navigation.findNavController(view)
                .navigate(R.id.action_startFragment_to_webViewFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}