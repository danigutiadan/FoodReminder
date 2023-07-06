package com.danigutiadan.foodreminder.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import java.io.File

object ImageUtils {

    fun takePictureFromCamera(context: Activity): Uri? {

        // Create a file to save the image
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "temp_image_${System.currentTimeMillis()}.jpg"
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