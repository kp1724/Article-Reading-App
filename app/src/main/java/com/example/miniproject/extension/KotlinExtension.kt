package com.example.miniproject.extension

import android.view.View

object KotlinExtension {

    object BooleanExtension {
        fun Boolean?.isFalse(): Boolean {
            if(this == null) return false
            return this == false
        }
    }

    object ViewExtension {
        fun View?.visible() {
            this?.visibility = View.VISIBLE
        }
        fun View?.gone() {
            this?.visibility = View.GONE
        }
        fun View?.invisible() {
            this?.visibility = View.INVISIBLE
        }
        fun View?.isVisible() = this?.visibility == View.VISIBLE
    }


}