package com.danigutiadan.foodreminder.features.add_food.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.danigutiadan.foodreminder.BaseActivity
import com.danigutiadan.foodreminder.databinding.ActivityAddFoodBinding
import com.danigutiadan.foodreminder.features.onboarding.ui.MY_PERMISSIONS_REQUEST_CAMERA
import com.danigutiadan.foodreminder.features.onboarding.ui.READ_EXTERNAL_STORAGE_PERMISSION_CODE
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CODE_IMAGE_PICKER
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.danigutiadan.foodreminder.utils.ImageUtils.getBitmapFromFilePath
import com.danigutiadan.foodreminder.utils.ImageUtils.getRotatedBitmapFromFilePath
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AddFoodActivity : BaseActivity() {
    private lateinit var addFoodViewModel: AddFoodViewModel
    private lateinit var binding: ActivityAddFoodBinding
    private var currentTimeMillis = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    fun takePicture(viewModel: AddFoodViewModel) {
        currentTimeMillis = Date().time.toString()
        addFoodViewModel = viewModel
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Ask for the permission
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA
            )
        } else {
            addFoodViewModel.imageUri = ImageUtils.takePictureFromCamera(this, currentTimeMillis)
        }


    }

    fun takeExistentPicture(viewModel: AddFoodViewModel) {
        addFoodViewModel = viewModel

        // Verificar si el permiso ya está otorgado
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePictureFromStorage()
        } else {
            // Solicitar permiso en tiempo de ejecución
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        }


    }

    private fun takePictureFromStorage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            when (requestCode) {
                REQUEST_CAPTURE_IMAGE -> {

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        addFoodViewModel.imageUri
                    )
                    val rotatedBitmap = getRotatedBitmapFromFilePath(
                        bitmap,
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +
                                "/${currentTimeMillis}.jpg"
                    )

                    val imagePath = ImageUtils.createDirectoryAndSaveImage(
                        this,
                        rotatedBitmap,
                        currentTimeMillis
                    )
                    addFoodViewModel.updateProfileBitmap(imagePath)
                    addFoodViewModel.bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)

                }

                REQUEST_CODE_IMAGE_PICKER -> {
                    addFoodViewModel.updateProfileBitmap(ImageUtils.getAbsolutePathFromUri(data?.data, contentResolver))
                    addFoodViewModel.bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)
                }


                else -> {}
            }
        else
            return
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addFoodViewModel.imageUri =
                        ImageUtils.takePictureFromCamera(this, currentTimeMillis)
                }
            }
            READ_EXTERNAL_STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        takePictureFromStorage()
                }

            }
        }
    }
}