package com.example.textrecognition.Presenter

import android.graphics.Bitmap
import com.example.textrecognition.Contract.MainContract
import com.example.textrecognition.Data.GoogleVision

class MainPresenter : MainContract.Presenter {


    override lateinit var view: MainContract.View
    override lateinit var googleVision: GoogleVision

    override fun returnResult(bitmap: Bitmap): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        var result : String?

        result = googleVision.Analyze(bitmap)

        return result
    }
}