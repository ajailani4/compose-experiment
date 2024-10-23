package com.ajailani.composeexperiment.ui.screen

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import com.ajailani.composeexperiment.R
import com.ajailani.composeexperiment.ui.common.component.CapturableScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScreenshotTestScreen() {
    val context = LocalContext.current
    val view = remember { mutableStateOf<View?>(null) }

    val writeImagePermissionState = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CapturableScreen(view = view) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Download image with random background")
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    modifier = Modifier.size(width = 200.dp, height = 100.dp),
                    painter = painterResource(id = R.drawable.img_test),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (writeImagePermissionState.status.isGranted) {
                            view.value?.captureScreen(context)
                        } else {
                            writeImagePermissionState.launchPermissionRequest()
                        }
                    }
                ) {
                    Text(text = "Download screen")
                }
            }
        }
    }
}

private fun View.captureScreen(context: Context) {
    try {
        var backgroundBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_background)
        backgroundBitmap = backgroundBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val contentBitmap = Bitmap
            .createBitmap(width, height, Bitmap.Config.ARGB_8888)
            .applyCanvas { draw(this) }

        val dx = (backgroundBitmap.width - contentBitmap.width * 3) / 2f
        val dy = (backgroundBitmap.height - contentBitmap.height * 3) / 2f
        val contentMatrix = Matrix().apply {
            setScale(3f, 3f)
            postTranslate(dx, dy)
        }

        val canvas = Canvas(backgroundBitmap)
        canvas.drawBitmap(contentBitmap, contentMatrix, null)

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "screenshot_test.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        context.contentResolver.apply {
            insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)?.let {
                openOutputStream(it)?.use { outputStream ->
                    backgroundBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
                }
            }
        }
    } catch (e: Exception) {
        Log.d("Screenshot exception: ", e.message.orEmpty())
    }
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use {
        bitmap.compress(format, quality, it)
        it.flush()
    }
}