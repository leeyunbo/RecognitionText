package com.example.textrecognition.Contract

import android.graphics.Bitmap

interface MainContract {

    interface View {
        val presenter : MainContract.Presenter

        fun requestResult()
        fun changeUI(result : String)


    }

    interface Presenter {
        val view : MainContract.View

        fun returnResult(bitmap : Bitmap) : String?

    }

}