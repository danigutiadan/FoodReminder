package com.danigutiadan.foodreminder.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import java.io.File


object ImageUtils {

    fun takePictureFromCamera(context: Activity): Uri? {

        // Create a file to save the image
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "TakenFromCamera.jpg"
        )

        // Create a Uri for the file
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        context.startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
        return uri
    }

//    fun captureImage() {
//        val intentCamera = Intent("android.media.action.IMAGE_CAPTURE")
//        val filePhoto = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
//        val imageUri = Uri.fromFile(filePhoto)
//        MyApplicationGlobal.imageUri = imageUri.getPath()
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//        startActivityForResult(intentCamera, TAKE_PICTURE)
//    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

//    fun rotateImage(imagePath: String, bitmap: Bitmap): Bitmap {
//        val exif = ExifInterface(imagePath)
//        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
//        val matrix = Matrix()
//
//        when (orientation) {
//            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
//            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
//            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
//        }

//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
//    }

     fun saveBitmapToGallery(bitmap: Bitmap, context: Context, displayName: String) {
        // Get a ContentResolver instance
        val resolver = context.contentResolver

        // Create a new ContentValues object
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        }

        // Insert the new image into the gallery
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // Open an OutputStream for the image Uri
        resolver.openOutputStream(imageUri ?: return)?.use { outputStream ->
            // Compress the bitmap to JPEG format and write it to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        }
    }
}