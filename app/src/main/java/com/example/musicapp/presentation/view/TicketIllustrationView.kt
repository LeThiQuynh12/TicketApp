
package com.example.musicapp.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class TicketIllustrationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private fun drawRoundShape(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        rx: Float,
        ry: Float,
        color: Int,
        rotation: Float = 0f
    ) {
        canvas.save()
        canvas.rotate(rotation, cx, cy)

        paint.color = color
        paint.style = Paint.Style.FILL

        canvas.drawRoundRect(
            cx - rx,
            cy - ry,
            cx + rx,
            cy + ry,
            ry * 0.7f,
            ry * 0.7f,
            paint
        )

        canvas.restore()
    }

    private fun drawStar(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        outerRadius: Float,
        innerRadius: Float,
        color: Int
    ) {
        val path = Path()

        for (i in 0 until 10) {
            val radius = if (i % 2 == 0) outerRadius else innerRadius
            val angle = Math.toRadians((i * 36 - 90).toDouble())

            val x = cx + cos(angle).toFloat() * radius
            val y = cy + sin(angle).toFloat() * radius

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        path.close()

        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (width == 0 || height == 0) return

        val sx = width / 360f
        val sy = height / 160f

        canvas.save()
        canvas.scale(sx, sy)

        // Background decorative shapes
        drawRoundShape(
            canvas, 47f, 78f, 35f, 48f,
            Color.rgb(226, 249, 239), -25f
        )

        drawRoundShape(
            canvas, 105f, 105f, 42f, 27f,
            Color.rgb(209, 244, 230), 15f
        )

        drawRoundShape(
            canvas, 256f, 52f, 38f, 35f,
            Color.rgb(216, 246, 241), 25f
        )

        drawRoundShape(
            canvas, 316f, 92f, 30f, 43f,
            Color.rgb(224, 247, 244), -20f
        )

        // Small decorative circles
        paint.color = Color.rgb(66, 230, 149)
        canvas.drawCircle(82f, 38f, 5f, paint)

        paint.color = Color.rgb(59, 178, 184)
        canvas.drawCircle(285f, 119f, 6f, paint)

        paint.color = Color.rgb(145, 225, 193)
        canvas.drawCircle(123f, 35f, 3f, paint)

        // Main ticket shadow
        paint.color = Color.argb(25, 18, 59, 53)
        canvas.drawRoundRect(
            RectF(83f, 43f, 280f, 120f),
            15f, 15f, paint
        )

        // Main ticket body
        paint.color = Color.rgb(255, 255, 255)
        canvas.drawRoundRect(
            RectF(80f, 38f, 277f, 115f),
            15f, 15f, paint
        )

        // Ticket outline
        paint.color = Color.rgb(205, 239, 224)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.5f
        canvas.drawRoundRect(
            RectF(80f, 38f, 277f, 115f),
            15f, 15f, paint
        )
        paint.style = Paint.Style.FILL

        // Ticket perforated line
        paint.color = Color.rgb(190, 226, 213)
        paint.strokeWidth = 2f
        paint.pathEffect = DashPathEffect(
            floatArrayOf(4f, 5f), 0f
        )

        canvas.drawLine(220f, 49f, 220f, 104f, paint)
        paint.pathEffect = null

        // Star background
        paint.color = Color.rgb(226, 249, 239)
        canvas.drawCircle(145f, 76f, 26f, paint)

        // Main star
        drawStar(
            canvas, 145f, 76f,
            19f, 9f,
            Color.rgb(32, 185, 138)
        )

        // Ticket information lines
        paint.color = Color.rgb(59, 178, 184)
        canvas.drawRoundRect(
            RectF(235f, 60f, 263f, 65f),
            3f, 3f, paint
        )

        paint.color = Color.rgb(160, 219, 200)
        canvas.drawRoundRect(
            RectF(235f, 72f, 259f, 76f),
            2f, 2f, paint
        )

        canvas.drawRoundRect(
            RectF(235f, 83f, 265f, 87f),
            2f, 2f, paint
        )

        // Small sparkle
        paint.color = Color.rgb(32, 185, 138)
        canvas.drawCircle(296f, 35f, 3f, paint)

        canvas.restore()
    }
}
