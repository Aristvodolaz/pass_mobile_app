package com.application.x_pass.ui

import android.Manifest
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.application.x_pass.R
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView

@Composable
fun QRScannerPermissionWrapper(goToBack: () -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasPermission = true
        } else {
            Toast.makeText(context, "Camera permission is required to scan QR codes.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (hasPermission) {
        QRScannerScreen(goToBack)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Please grant camera permission to scan QR codes.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text(text = "Go to Settings")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun havePrevie(){
//    QRScannerScreen()
//}

@Composable
fun QRScannerScreen(goToBack: () -> Unit) {
    val context = LocalContext.current

    var isFlashEnabled by remember { mutableStateOf(false) }
    var isEntranceChecked by remember { mutableStateOf(true) }

    var scanResult by remember { mutableStateOf<String?>(null) }

    val barcodeView = remember {
        BarcodeView(context).apply {
            cameraSettings.isBarcodeSceneModeEnabled = true // Use Camera2 API
        }
    }

    LaunchedEffect(barcodeView) {
        try {
            barcodeView.decodeContinuous(object : BarcodeCallback {
                override fun barcodeResult(result: BarcodeResult?) {
                    result?.let {
                        if (scanResult != it.text) {
                            scanResult = it.text
                            val entranceText = if (isEntranceChecked) "Entrance" else "Exit"
                            Toast.makeText(context, "Scanned: ${it.text} for $entranceText", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>?) {}
            })
            barcodeView.resume()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to open camera: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(barcodeView) {
        onDispose {
            barcodeView.pause()
        }
    }



    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        toolBar(goToBack)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.pleade_use_qr),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.check_entrance),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = isEntranceChecked,
                    onCheckedChange = { isEntranceChecked = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Green,
                        uncheckedThumbColor = Color.Red
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            AndroidView(
                factory = { barcodeView },
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = {
                    isFlashEnabled = !isFlashEnabled
                    barcodeView.setTorch(isFlashEnabled)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .size(56.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {
                Icon(
                    imageVector = if (isFlashEnabled) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                    contentDescription = "Toggle Flash",
                    tint = Color.White
                )
            }
        }

        scanResult?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Scanned: $it", color = Color.White)
            }
        }
    }
}
@Composable
fun toolBar(onBackClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White) ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Image(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = stringResource(R.string.scanning),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
