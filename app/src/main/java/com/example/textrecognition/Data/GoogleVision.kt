package com.example.textrecognition.Data

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.graphics.Bitmap
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.Image
import android.os.Build
import android.provider.MediaStore.Images.ImageColumns.ORIENTATION
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import androidx.annotation.RequiresApi
import com.example.textrecognition.Contract.MainContract
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.io.IOException
import java.io.PrintStream
import java.util.*


class GoogleVision {

    private val ORIENTATIONS = SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    public fun Analyze(bitmap: Bitmap,presenter : MainContract.Presenter){
        Bitmap.createScaledBitmap(bitmap, 720, 1280, true)
        var text: String? = null
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        val options = FirebaseVisionCloudDocumentRecognizerOptions.Builder()
            .setLanguageHints(Arrays.asList("ko", "hi"))
            .build()
        val detector = FirebaseVision.getInstance().getCloudDocumentTextRecognizer(options)

        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionDocumentText ->
                text = onSuccess(bitmap, firebaseVisionDocumentText)
                presenter.returnResultToView(text)
            }
            .addOnFailureListener {
                Log.e("LOG : ", "addOnFailureListener")
            }

    }

    fun onSuccess(originalCameraImage: Bitmap?, result: FirebaseVisionDocumentText) : String? {
        val resultText = result.text
        val results = mutableListOf("")
        for (block in result.blocks) {
            val blockText = block.text
            results.add(blockText)
        }

        println(results.joinToString())


        return results.joinToString()


    }
}


