package com.mygroup.nasugburoots.ui


import android.app.Application
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.internal.MapsforgeThemes
import java.io.File
import java.io.FileInputStream

private const val MAP_FILE_NAME =
    "planet_120.348_14.063_d073dc7d.map"

@Composable
fun NasugbuMapScreen() {

    val mapViewState = remember { mutableStateOf<MapView?>(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->

            try {
                AndroidGraphicFactory.createInstance(
                    ctx.applicationContext as Application
                )

                // Copy offline map from assets to app cache
                val mapFile = File(ctx.cacheDir, MAP_FILE_NAME)

                if (!mapFile.exists()) {
                    ctx.assets.open(MAP_FILE_NAME).use { input ->
                        mapFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                // Create Mapsforge MapView
                val mapView = MapView(ctx)
                mapViewState.value = mapView

                // Create tile cache
                val tileCache = AndroidUtil.createTileCache(
                    ctx,
                    "nasugbu-map-cache",
                    mapView.model.displayModel.tileSize,
                    1f,
                    mapView.model.frameBufferModel.overdrawFactor
                )

                // Read offline map
                val mapDataStore = MapFile(FileInputStream(mapFile))

                // Render map
                val mapLayer = TileRendererLayer(
                    tileCache,
                    mapDataStore,
                    mapView.model.mapViewPosition,
                    AndroidGraphicFactory.INSTANCE
                )

                mapLayer.setXmlRenderTheme(MapsforgeThemes.DEFAULT)

                mapView.layerManager.layers.add(mapLayer)

                // Nasugbu Landing Monument marker
                val markerView = TextView(ctx).apply {
                    text = "📍"
                    textSize = 32f
                    setPadding(8, 8, 8, 8)

                    setOnClickListener {
                        Toast.makeText(
                            ctx,
                            "Nasugbu Landing Monument",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                mapView.addView(
                    markerView,
                    MapView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        LatLong(14.07125, 120.62498),
                        MapView.LayoutParams.Alignment.BOTTOM_CENTER
                    )
                )

                mapView.setCenter(LatLong(14.07125, 120.62498))
                mapView.setZoomLevel(16)
                mapView.setBuiltInZoomControls(true)
                mapView.mapScaleBar.isVisible = true

                mapView

            } catch (e: Exception) {
                TextView(ctx).apply {
                    text = "Unable to load Nasugbu map: ${e.message}"
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            mapViewState.value?.destroyAll()
            AndroidGraphicFactory.clearResourceMemoryCache()
        }
    }
}
