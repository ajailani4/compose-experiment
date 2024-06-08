package com.ajailani.experiment.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ajailani.experiment.R
import com.ajailani.experiment.ui.theme.BlueGopay
import com.ajailani.experiment.ui.theme.OrangeTopUp
import com.ajailani.experiment.ui.theme.RedError

@Composable
fun EWalletItemCard(
    uiState: UiState
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 130.dp)
            .border(
                width = 2.dp,
                color = BlueGopay,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(color = BlueGopay)
            )
            Box {
                Image(
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 7.dp, y = (-15).dp),
                    painter = painterResource(id = R.drawable.img_gopay),
                    alpha = 0.2f,
                    contentDescription = "GoPay logo"
                )
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "GoPay",
                        fontSize = 16.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        when (uiState) {
                            UiState.Success -> {
                                Column {
                                    Text(
                                        text = "E-wallet balance",
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Rp15.000.000",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            UiState.Failed -> {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Default.Error,
                                        tint = RedError,
                                        contentDescription = "Error icon"
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "Gagal terhubung",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.align(Alignment.Bottom),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            when (uiState) {
                                UiState.Success -> {
                                    Icon(
                                        imageVector = Icons.Outlined.AccountBalanceWallet,
                                        tint = OrangeTopUp,
                                        contentDescription = "E-wallet top up icon"
                                    )
                                    Spacer(modifier = Modifier.width(7.dp))
                                    Text(
                                        text = "Top up",
                                        color = OrangeTopUp,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                UiState.Failed -> {
                                    Icon(
                                        imageVector = Icons.Outlined.HelpOutline,
                                        contentDescription = "Help icon"
                                    )
                                    Spacer(modifier = Modifier.width(7.dp))
                                    Text(
                                        text = "Lihat Detail",
                                        color = OrangeTopUp,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class UiState {
    Success, Failed
}

@Preview(showBackground = true)
@Composable
fun PreviewEWalletItemCard() {
    Column(modifier = Modifier.padding(10.dp)) {
        EWalletItemCard(UiState.Success)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEWalletItemCardConnectionFailed() {
    Column(modifier = Modifier.padding(10.dp)) {
        EWalletItemCard(UiState.Failed)
    }
}