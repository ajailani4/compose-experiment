package com.ajailani.composeexperiment

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.ajailani.composeexperiment.ui.navigation.Navigation
import com.ajailani.composeexperiment.ui.screen.experiment.ExperimentScreen
import com.ajailani.composeexperiment.ui.theme.ComponentSlicingTheme
import com.ajailani.composeexperiment.util.MixpanelUtil

class MainActivity : FragmentActivity() {
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MixpanelUtil.reset()

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ComponentSlicingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//
//                    Navigation(navController)

                    ExperimentScreen()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            Log.d("TAG", "Tag: $tag")

            val nfcA = NfcA.get(tag)

            try {
                nfcA.connect()
                val command = byteArrayOf(0x30.toByte(), 0x01.toByte())

                val data = nfcA.transceive(command)

                Log.d("TAG", "Data: ${data.toList()}")
                Log.d("TAG", "Parsed data: ${String(data, Charsets.UTF_8)}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                nfcA.close()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        enableForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()

        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun enableForegroundDispatch() {
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_MUTABLE
            )

        val intentFilters = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED))
        val techList = arrayOf<Array<String>>()

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, techList)
    }
}