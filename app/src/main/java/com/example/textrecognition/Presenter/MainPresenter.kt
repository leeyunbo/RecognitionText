package com.example.textrecognition.Presenter

import android.graphics.Bitmap
import com.example.textrecognition.Contract.MainContract
import com.example.textrecognition.Data.GoogleVision

class MainPresenter : MainContract.Presenter {


    override lateinit var view: MainContract.View
    override lateinit var googleVision: GoogleVision

    override fun returnResult(bitmap: Bitmap) {
        var result : String?

        googleVision.Analyze(bitmap,this)
    }

    override fun returnResultToView(result : String?) {
        view.changeUI(result)
    }
}