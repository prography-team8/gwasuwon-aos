package com.prography.qr

import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.prography.domain.qr.CommonQrEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
class ShowQrScannerSubscriber(
    private val activity: ComponentActivity,
    private val qrEventFlow: MutableSharedFlow<CommonQrEvent>
) {
    private val qrScannerLauncher: ActivityResultLauncher<Intent> = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intentResult: IntentResult = IntentIntegrator.parseActivityResult(
            result.resultCode,
            result.data
        )
        intentResult.contents?.let {
            activity.lifecycleScope.launch {
                qrEventFlow.emit(CommonQrEvent.GetOnSuccessQr(it))
            }
        }
    }
    private val requestPermissionLauncher: ActivityResultLauncher<String> = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startQRScanner()
        } else {
            Toast.makeText(activity, "카메라 권한 설정을 확인하세요.", Toast.LENGTH_SHORT).show()
        }
    }
    fun observeEvent(){
        qrEventFlow.onEach {
            when (it) {
                is CommonQrEvent.RequestQrScan -> {
                    if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    } else {
                        startQRScanner()
                    }
                }

                else -> Unit
            }
        }.launchIn(activity.lifecycleScope)
    }
    private fun startQRScanner() {
        val integrator = IntentIntegrator(activity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.setOrientationLocked(false)
        integrator.setCaptureActivity(CustomScannerActivity::class.java)
        qrScannerLauncher.launch(integrator.createScanIntent())
    }
}