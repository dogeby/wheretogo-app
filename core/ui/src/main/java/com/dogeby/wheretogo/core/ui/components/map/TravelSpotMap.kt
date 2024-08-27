package com.dogeby.wheretogo.core.ui.components.map

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.overlay.Marker

@Composable
fun TravelSpotMap(
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    defaultZoom: Double = 16.0,
    minZoom: Double = 11.0,
    maxZoom: Double = 18.0,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        val latLng = LatLng(latitude, longitude)
        val options = NaverMapOptions()
            .camera(CameraPosition(latLng, defaultZoom))
            .allGesturesEnabled(false)
            .minZoom(minZoom)
            .maxZoom(maxZoom)
        MapView(context, options).apply {
            getMapAsync {
                Marker().apply {
                    width = 78
                    height = 100
                    position = latLng
                    map = it
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> {
                    mapView.visibility = View.VISIBLE
                    mapView.onStart()
                }
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> {
                    mapView.visibility = View.GONE
                    mapView.onStop()
                }
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                Lifecycle.Event.ON_ANY -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun TravelSpotMapPreview() {
    TravelSpotMap(
        latitude = 34.8043478048,
        longitude = 128.4207095494,
    )
}
