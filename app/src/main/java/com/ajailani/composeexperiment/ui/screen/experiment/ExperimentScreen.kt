package com.ajailani.composeexperiment.ui.screen.experiment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajailani.composeexperiment.ui.common.component.FlyingBoxScreen

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExperimentScreen() {
//     var creditCardNumber by remember { mutableStateOf("") }
//    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        /*CreditCardTextField(
            modifier = Modifier.fillMaxWidth(),
            value = creditCardNumber,
            setValue = { creditCardNumber = it }
        )*/

       /* CustomTabRow { selectedTabIndex ->
            when (selectedTabIndex) {
                0 -> Tab1Screen()

                1 -> Tab2Screen()
            }
        }*/

//        LineChart(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp),
//            records = listOf(250.0, 0.0, 340.0, 680.0, 520.0, 930.0, 600.0, 458.0, 785.0, 840.0, 1200.0, 670.0)
//        )
//
//        SwipeToDismissScreen()
//        DragAndDropScreen()
//
//        DrawingScreen()
//        CustomFlowRow()
    }
//    FloatingBoxScreen()
//    NestedScrollScreen()
//    StickySectionScreen()
//    SwapCubeScreen()
    FlyingBoxScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewExperimentScreen() {
    ExperimentScreen()
}