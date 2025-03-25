package com.example.facedetection

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class FaceOverlayView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private var faceBoundingBoxes: List<RectF> = emptyList()

    // Üzlərin bounding box-larını kamera ölçüləri ilə uyğunlaşdırırıq
    fun setFaces(faces: List<Rect>, imageWidth: Int, imageHeight: Int) {
        faceBoundingBoxes = faces.map { face ->
            scaleBoundingBox(face, imageWidth, imageHeight)
        }
        invalidate() // Yenidən çəkmək üçün
    }

    // Bounding box-ı scale edir və en faktorunu artırırıq
    private fun scaleBoundingBox(face: Rect, imageWidth: Int, imageHeight: Int): RectF {
        val scaleX = width.toFloat() / imageWidth
        val scaleY = height.toFloat() / imageHeight

        // Bounding box-ı böyütmək üçün daha yüksək scaleFactor
        val scaleFactorX = 1.2f // X yönü üçün daha çox böyütmək
        val scaleFactorY = 1.1f // Y yönü üçün normal böyütmək

        return RectF(
            face.left * scaleX * scaleFactorX,  // X istiqamətində daha çox böyüt
            face.top * scaleY * scaleFactorY,   // Y istiqamətində böyüt
            face.right * scaleX * scaleFactorX, // X istiqamətində daha çox böyüt
            face.bottom * scaleY * scaleFactorY // Y istiqamətində böyüt
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (box in faceBoundingBoxes) {
            canvas.drawRect(box, paint)
        }
    }
}

