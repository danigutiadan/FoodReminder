package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object ImageUtils {

    fun takePictureFromCamera(context: Activity, fileName: String): Uri? {

        // Create a file to save the image
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "${fileName}.jpg"
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

     fun getAbsolutePathFromUri(uri: Uri?, contentResolver: ContentResolver): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = uri?.let { contentResolver.query(it, projection, null, null, null) }

        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }

        return null
    }

    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap != null) {
            val MAX_IMAGE_SIZE = 1024 * 1024 // 1MB
            val outputStream = ByteArrayOutputStream()
            var compressionQuality = 90 // Rango de calidad de compresión (0-100)
            var imageSize = 0

            do {
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
                imageSize = outputStream.size()

                // Reducir la calidad de la compresión si el tamaño de la imagen aún es demasiado grande
                compressionQuality -= 10
            } while (imageSize > MAX_IMAGE_SIZE && compressionQuality >= 10)

            return outputStream.toByteArray()
        }
        return null
    }

    fun createDirectoryAndSaveImage(context: Context, imageToSave: Bitmap, fileName: String): String? {
       val fileName = "$fileName.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        try {
            val file = File(storageDir, fileName)
            val outStream = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }


//    fun createDirectoryAndSaveImage(imageToSave: Bitmap, fileName: String): String? {
//        val direct = File(Environment.getExternalStorageDirectory().toString() + "/FoodReminder")
//        if (!direct.exists()) {
//            val wallpaperDirectory = File("/sdcard/FoodReminder/")
//            wallpaperDirectory.mkdirs()
//        }
//        val file = File("/sdcard/FoodReminder/", "$fileName.jpg")
//        if (file.exists()) {
//            file.delete()
//        }
//        try {
//            val out = FileOutputStream(file)
//            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
//            out.flush()
//            out.close()
//            return file.absolutePath
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//         return null
//     }

    fun getBitmapFromFilePath(contentResolver: ContentResolver, data: Intent?): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
        val rotatedBitmap =
            data?.data?.let {
                getImageFilePath(it, contentResolver)?.let {
                    getRotatedBitmapFromFilePath(
                        bitmap,
                        it
                    )
                }
            }
        return rotatedBitmap ?: bitmap
    }

    fun getBitmapFromFilePath(filePath: String): Bitmap? {
        return BitmapFactory.decodeFile(filePath)
    }


    fun imageUrlToBitmap(context: Context, imageUrl: String, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource) // Pasar el Bitmap descargado a la devolución de llamada
                }
            })
    }


    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }



    fun getRotatedBitmapFromFilePath(bitmap: Bitmap, imagePath: String): Bitmap {
        val exif = ExifInterface(imagePath)
        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getImageFilePath(uri: Uri, contentResolver: ContentResolver): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }

        return null
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
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