package com.ajailani.composeexperiment.ui.screen.experiment

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import androidx.pdf.viewer.fragment.PdfViewerFragment
import com.ajailani.composeexperiment.databinding.PdfFragmentLayoutBinding

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
@Composable
fun PdfViewerScreen() {
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    val pickFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { pdfUri = it }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            onClick = { pickFileLauncher.launch("application/pdf") }
        ) {
            Text(text = "Open PDF")
        }

        pdfUri?.let {
            /*AndroidViewBinding(factory = PdfFragmentLayoutBinding::inflate) {
                val fragment = pdfFragment.getFragment<PdfViewerFragment>()
                fragment.documentUri = it
            }*/

            AndroidFragment<PdfViewerFragment>(
                modifier = Modifier.fillMaxSize(),
                arguments = bundleOf("documentUri" to it)
            ) { pdfViewerFragment ->
                Log.d("PdfViewerScreen", "pdfUri: ${pdfViewerFragment.documentUri}")
            }
        }
    }
}