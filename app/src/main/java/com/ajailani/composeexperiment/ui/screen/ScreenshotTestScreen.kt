package com.ajailani.composeexperiment.ui.screen

import android.graphics.Bitmap
import android.os.Environment
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
    val view = remember { mutableStateOf<View?>(null) }

    val writeImagePermissionState = rememberPermissionState(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CapturableScreen(view = view) {
            Column(
                modifier = Modifier.background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                (1..8).forEach {
                    Image(
                        modifier = Modifier.size(width = 200.dp, height = 100.dp),
                        painter = painterResource(id = R.drawable.img_test),
                        contentDescription = null
                    )
                    Text(text = "Image $it")
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (writeImagePermissionState.status.isGranted) {
                            view.value?.captureScreen()
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

private fun View.captureScreen() {
    try {
        val bitmap = Bitmap
            .createBitmap(width, height, Bitmap.Config.ARGB_8888)
            .applyCanvas { draw(this) }

        bitmap.let {
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "screenshot_test.jpg"
            ).writeBitmap(it, Bitmap.CompressFormat.JPEG, 85)
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