package com.example.textrecognition.Contract

import android.graphics.Bitmap
import com.example.textrecognition.Data.GoogleVision

interface MainContract {

    interface View {
        val presenter : MainContract.Presenter

        fun getBitmapFromCamera()
        fun requestResult()
        fun changeUI(result : String?)
        fun rotateImage(bitmap : Bitmap, angle : Float) : Bitmap


    }

    interface Presenter {
        val view : MainContract.View
        val googleVision : GoogleVision
        fun returnResult(bitmap : Bitmap)
        fun returnResultToView(result : String?)

    }

}