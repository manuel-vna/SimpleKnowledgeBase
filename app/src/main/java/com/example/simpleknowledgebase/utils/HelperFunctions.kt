package com.example.simpleknowledgebase.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


class HelperFunctions {

    companion object {
        // function hideKeyboard() added as an extension function of class Fragment
        fun Fragment.hideKeyboard() {
            //scope function let{} (lambda) assures that its body is only executed if view != null
            view?.let { activity?.hideKeyboard(it) }
        }
        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }
        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}