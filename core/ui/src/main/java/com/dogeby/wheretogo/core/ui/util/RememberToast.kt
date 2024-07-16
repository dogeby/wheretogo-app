package com.dogeby.wheretogo.core.ui.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SHORT_DELAY = 2_000L
private const val LONG_DELAY = 3_500L
private const val DELAY_OFFSET = 500L

@Composable
fun rememberToast(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): (String, Int) -> Unit {
    var isToastShowing by remember { mutableStateOf(false) }

    return remember(context, coroutineScope) {
        { message: String, duration: Int ->
            if (!isToastShowing) {
                isToastShowing = true
                Toast.makeText(context, message, duration).show()

                val delayDuration = when (duration) {
                    Toast.LENGTH_SHORT -> SHORT_DELAY
                    Toast.LENGTH_LONG -> LONG_DELAY
                    else -> SHORT_DELAY
                }

                coroutineScope.launch {
                    delay(delayDuration + DELAY_OFFSET)
                    isToastShowing = false
                }
            }
        }
    }
}
