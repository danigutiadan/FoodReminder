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
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CAPTURE_IMAGE
import com.danigutiadan.foodreminder.features.onboarding.ui.REQUEST_CODE_IMAGE_PICKER
import com.danigutiadan.foodreminder.utils.ImageUtils
import com.danigutiadan.foodreminder.utils.ImageUtils.rotateBitmap
import com.dokar.sheets.BottomSheetState
import com.dokar.sheets.BottomSheetValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFoodActivity : BaseActivity() {
    private lateinit var addFoodViewModel: AddFoodViewModel
    private lateinit var binding: ActivityAddFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    fun takePicture(viewModel: AddFoodViewModel) {
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
            addFoodViewModel.imageUri = ImageUtils.takePictureFromCamera(this)
        }


    }

    fun takeExistentPicture(viewModel: AddFoodViewModel) {
        addFoodViewModel = viewModel
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
                    val rotatedBitmap = rotateBitmap(
                        bitmap,
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() +
                                "/TakenFromCamera.jpg"
                    )
                    addFoodViewModel.updateProfileBitmap(rotatedBitmap)
                    addFoodViewModel.bottomSheetState.value =
                        BottomSheetState(initialValue = BottomSheetValue.Collapsed)

                }

                REQUEST_CODE_IMAGE_PICKER -> {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data?.data)
                    val rotatedBitmap =
                        data?.data?.let {
                            ImageUtils.getImageFilePath(it, contentResolver)?.let {
                                rotateBitmap(
                                    bitmap,
                                    it
                                )
                            }
                        }
                    addFoodViewModel.updateProfileBitmap(rotatedBitmap ?: bitmap)
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
                    addFoodViewModel.imageUri = ImageUtils.takePictureFromCamera(this)
                }
            }
        }
    }
}