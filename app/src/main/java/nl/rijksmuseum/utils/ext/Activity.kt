package nl.rijksmuseum.utils.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.*

fun Context.detectNetworkHealth(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    } else {
        val ni = connectivityManager.activeNetworkInfo
        ni != null && ni.isConnected
    }
}

fun Activity.setWhiteStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
    }
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Activity?.toast(@StringRes resId: Int) =
    Toast.makeText(this, this?.getString(resId), Toast.LENGTH_SHORT).show()

fun Activity.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
fun Activity?.longToast(@StringRes resId: Int) =
    Toast.makeText(this, this?.getString(resId), Toast.LENGTH_LONG).show()