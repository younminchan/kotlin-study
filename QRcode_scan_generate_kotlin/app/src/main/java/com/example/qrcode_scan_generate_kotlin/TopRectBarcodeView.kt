package com.example.qrcode_scan_generate_kotlin

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.Size
import kotlin.math.roundToInt

/**
 * Scanner 위치를 custom 설정, 배치하는 Class
 *
 * 참고자료
 * https://medium.com/@mhmdawaddd/change-the-frame-position-and-size-of-the-zxing-barcode-scanning-library-for-android-95747919cfbe
 * */
class TopRectBarcodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BarcodeView(context, attrs, defStyleAttr) {

    override fun getFramingRectSize(): Size {
        return Size(Resources.getSystem().displayMetrics.widthPixels, dpToPx(212))
    }

    private fun dpToPx(dp: Int): Int {
        return ((dp * Resources.getSystem().displayMetrics.density).roundToInt())
    }


    override fun calculateFramingRect(container: Rect?, surface: Rect?): Rect {
        /** 예제 코드는 정사각형을 기준으로 한 코드예시 */

        // create new rect instance that hold the container.
        val intersection = Rect(container).apply {
            //Left Margin
            left = dpToPx(30)
            //Top Margin
            top = dpToPx(100)
            //Right Margin
            right = framingRectSize.width - dpToPx(30)
            //Bottom position (좌표 계산이 필요)
            // (중요) 가로 길이에서 top만큼 더 밑으로 내려가야함!
            bottom = (framingRectSize.width - dpToPx(60)) + top
        }

        return intersection
    }
}