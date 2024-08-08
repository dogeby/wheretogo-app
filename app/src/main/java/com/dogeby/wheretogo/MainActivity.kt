package com.dogeby.wheretogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dogeby.wheretogo.core.common.networkmonitor.NetworkMonitor
import com.dogeby.wheretogo.ui.theme.WheretogoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WheretogoTheme {
                WheretogoApp(networkMonitor = networkMonitor)
            }
        }
    }
}
