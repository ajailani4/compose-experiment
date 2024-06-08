package com.ajailani.experiment.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ajailani.experiment.R
import com.ajailani.experiment.ui.common.component.CustomDialog
import com.ajailani.experiment.ui.common.component.ImageCropperOverlay
import com.ajailani.experiment.ui.common.component.ZoomableImage

/** This hasn't been done yet */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditorScreen() {
    var croppedImageSize by remember { mutableStateOf(Size(0f, 0f)) }
    var scaleXResult by remember { mutableFloatStateOf(0f) }
    var scaleYResult by remember { mutableFloatStateOf(0f) }
    var translationXResult by remember { mutableFloatStateOf(0f) }
    var translationYResult by remember { mutableFloatStateOf(0f) }
    val (imageResultDialogVis, setImageResultDialogVis) = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ZoomableImage(
            modifier = Modifier.height(600.dp),
            image = painterResource(id = R.drawable.img_test_portrait),
            processGraphicsLayerChange = { scaleX, scaleY, translationX, translationY ->
                scaleXResult = scaleX
                scaleYResult = scaleY
                translationXResult = translationX
                translationYResult = translationY
            }
        )
        ImageCropperOverlay(
            processCroppedImage = { size ->
                croppedImageSize = size
            }
        )
        Button(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = { setImageResultDialogVis(!imageResultDialogVis)}
        ) {
            Text(text = "Crop image")
        }
    }

    if (imageResultDialogVis) {
        CustomDialog(
            onDismissRequest = { setImageResultDialogVis(false) },
            content = {
                Column {
                    Image(
                        modifier = Modifier
                            .size(
                                width = croppedImageSize.width.dp,
                                height = croppedImageSize.height.dp
                            )
                            .graphicsLayer {
                                scaleX = scaleXResult
                                scaleY = scaleYResult
                                translationX = translationXResult
                                translationY = translationYResult
                            },
                        painter = painterResource(id = R.drawable.img_test_portrait),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Image result"
                    )
                }
            }
        )
    }
}