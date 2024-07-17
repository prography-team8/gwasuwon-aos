package com.prography.qr

import android.app.Activity
import android.os.Bundle
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * Created by MyeongKi.
 */
class CustomScannerActivity : Activity() {

    private lateinit var barcodeView: BarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_scanner_activity)

        barcodeView = findViewById(R.id.zxing_barcode_scanner)
        barcodeView.decodeContinuous(callback)
    }

    private val callback = BarcodeCallback { result: BarcodeResult ->
        result.text?.let {
            val resultIntent = intent
            resultIntent.putExtra("SCAN_RESULT", it)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}