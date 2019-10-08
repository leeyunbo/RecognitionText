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
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import java.io.PrintStream
import java.util.*


class GoogleVision   {

    private val ORIENTATIONS = SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0,90)
        ORIENTATIONS.append(Surface.ROTATION_90,0)
        ORIENTATIONS.append(Surface.ROTATION_180,270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    public fun Analyze(bitmap : Bitmap) : String? {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        var resultText : String? = null

        val options = FirebaseVisionCloudDocumentRecognizerOptions.Builder()
            .setLanguageHints(Arrays.asList("ko","윤복"))
            .build()
        val detector = FirebaseVision.getInstance().getCloudDocumentTextRecognizer(options)

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionDocumentText ->
                resultText = firebaseVisionDocumentText.text
                Log.i("Analyze result : ",resultText)
            }
            .addOnFailureListener { // Task failed
            }


        return resultText


    }
/*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(CameraAccessException::class)
    private fun getRotationCompensation(cameraId : String, activity : Activity, context : Context) : Int {

        val deviceRotation = activity.windowManager.defaultDisplay.rotation
        var rotationCompensation = ORIENTATIONS.get(deviceRotation)

        val cameraManager = context.getSystemService(CAMERA_SERVICE) as CameraManager
        val sensorOrientation = cameraManager
            .getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360

        val result : Int
        when (rotationCompensation) {
            0 -> result = FirebaseVisionImageMetadata.ROTATION_0
            90 -> result = FirebaseVisionImageMetadata.ROTATION_90
            180 -> result = FirebaseVisionImageMetadata.ROTATION_180
            270 -> result = FirebaseVisionImageMetadata.ROTATION_270
            else -> {
                result = FirebaseVisionImageMetadata.ROTATION_0
                Log.e(TAG, "Bad rotation value : $rotationCompensation")
            }
        }
        return result
    }*/







}