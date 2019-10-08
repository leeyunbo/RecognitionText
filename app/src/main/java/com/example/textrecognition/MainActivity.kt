package com.example.textrecognition

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.Nullable
import android.widget.ImageView
import com.example.textrecognition.Contract.MainContract
import com.example.textrecognition.Data.GoogleVision
import com.example.textrecognition.Presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(),MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    override fun getBitmapFromCamera() {
        val cameraIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,1)
    }

    override fun requestResult(bitmap: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeUI(result: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter().apply {
            view = this@MainActivity
            googleVision = GoogleVision()
        }

        recognition_image_view.setOnClickListener{
            getBitmapFromCamera()
        }

        start_recognition_button.setOnClickListener{
            val bitmap : Bitmap = recognition_image_view.drawable as Bitmap
            presenter.returnResult(bitmap)
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, @Nullable data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != 0) {
            if(data != null) {
                if (requestCode == 1 && !data?.equals(null)) {
                    try {
                        var profileBitmap = data.extras.get("data") as Bitmap
                        recognition_image_view.setImageBitmap(profileBitmap)
                        recognition_image_view.scaleType = ImageView.ScaleType.FIT_XY
                    } catch (e: Exception) {
                        return
                    }
                }
            }
        }
    }
}
