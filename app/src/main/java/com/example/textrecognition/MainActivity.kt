package com.example.textrecognition

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.textrecognition.Contract.MainContract
import com.example.textrecognition.Data.GoogleVision
import com.example.textrecognition.Presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(),MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter().apply {
            view = this@MainActivity
            googleVision = GoogleVision()
        }

        recognition_image_view.setOnClickListener{ //이미지 뷰 클릭 하면, 카메라 등장
            getBitmapFromCamera()
        }

        start_recognition_button.setOnClickListener{           // recognition 요청 버튼 클릭
            requestResult()
        }
    }

    override fun getBitmapFromCamera() { //카메라에서 비트맵 가져오자
        val cameraIntent : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,1)
    }

    override fun requestResult() { //텍스트 인식 결과 받아오기
        val bitmap : Bitmap = recognition_image_view.drawable as Bitmap
        changeUI(presenter.returnResult(bitmap) as String)
    }

    override fun changeUI(result: String) { // 텍스트 인식 결과 UI 변경
        result_text_view.setText(result)
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
