package i.krishnasony.customizeorders.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.NumberFormat
import java.util.*

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Context.progressDialog(message: String): AlertDialog {
    val llPadding = 30
    val ll = LinearLayout(this)
    ll.orientation = LinearLayout.HORIZONTAL
    ll.setPadding(llPadding, llPadding, llPadding, llPadding)
    ll.gravity = Gravity.START
    var llParam = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    llParam.gravity = Gravity.START
    ll.layoutParams = llParam

    val progressBar = ProgressBar(this)
    progressBar.isIndeterminate = true
    progressBar.indeterminateDrawable
        .setColorFilter(Color.parseColor("#f57f17"), PorterDuff.Mode.SRC_IN)
    progressBar.setPadding(0, 0, llPadding, 0)
    progressBar.layoutParams = llParam

    llParam = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    llParam.gravity = Gravity.CENTER
    val tvText = TextView(this)
    tvText.text = message
    tvText.setTextColor(Color.parseColor("#000000"))
    tvText.textSize = 20.toFloat()
    tvText.layoutParams = llParam

    ll.addView(progressBar)
    ll.addView(tvText)

    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(ll)

    val dialog = builder.create()
    val window = dialog.window
    if (window != null) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
    }
    return dialog
}

fun Double.getRupeeFormat():String{
    val format = NumberFormat.getCurrencyInstance(Locale("en", "in"))
    return format.format(this)
}

fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}