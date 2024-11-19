
package com.thomasw.precision
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NotesPageWithDrawing() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                DrawingCanvasView(context).apply {
                    setBackgroundColor(Color.WHITE) // Optional: Explicitly set the background
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    // Paint object for styling and drawing paths
    private val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    // Path object to store the drawing path
    private val path = Path()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Check if the input is from a stylus
        if (event.getToolType(0) == MotionEvent.TOOL_TYPE_STYLUS || event.getToolType(0) == MotionEvent.TOOL_TYPE_FINGER) {
            val x = event.x
            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x, y)
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x, y)
                }
                MotionEvent.ACTION_UP -> {
                    // Nothing to do here, path will stay on the canvas
                }
                else -> return false
            }
            // Request to redraw the view
            invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the current path on the canvas
        canvas.drawPath(path, paint)
    }

    // Method to clear the canvas
    fun clearCanvas() {
        path.reset()
        invalidate()
    }
}     