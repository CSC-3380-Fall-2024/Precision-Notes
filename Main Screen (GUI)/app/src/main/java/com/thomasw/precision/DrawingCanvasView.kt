package com.thomasw.precision
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

//pens and inks
import  android.app.Dialog
import android.widget.ImageButton
import androidx.core.content.ContextCompat


@Composable
fun NotesPageWithDrawing() {
    println("Navigating to NotesPageWithDrawing DRAWING CANVAS")
    var menuExpanded by remember { mutableStateOf(false) } // State to control dropdown visibility

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Dropdown menu trigger
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd) // Position at the top-right corner
                .padding(16.dp)
                .clickable { menuExpanded = true } // Open the menu on click
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = Color.Black
            )
        }

        // Dropdown menu
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(onClick = {
                menuExpanded = false
                println("Clear Canvas Clicked")
                // Call a function like drawingCanvasView.clearCanvas() here
            }) {
                Text("Clear Canvas")
            }
            DropdownMenuItem(onClick = {
                menuExpanded = false
                println("Save Drawing Clicked")
                // Add functionality for saving the drawing here
            }) {
                Text("Save Drawing")
            }
        }
    
        AndroidView(
            factory = { context ->
                DrawingCanvasView(context).apply {
                    setBackgroundColor(Color.WHITE)
                    this.isFocusable = true
                    this.isFocusableInTouchMode = true// Optional: Explicitly set the background
                }

            },
            modifier = Modifier.fillMaxSize()

        )
    }
}
class DrawingCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled) {
            when (keyCode) {
                KeyEvent.KEYCODE_DEL -> {
                    // Handle backspace
                    if (currentText.isNotEmpty()) {
                        currentText.deleteCharAt(currentText.length - 1)
                    }
                }
                else -> {
                    val char = event?.unicodeChar?.toChar()
                    if (char != null && char.isLetterOrDigit()) {
                        currentText.append(char)
                    }
                }
            }
            invalidate()
            return true // Indicate the event was handled
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (typingEnabled && keyCode == KeyEvent.KEYCODE_ENTER) {
            finishTyping() // Finalize the current text entry
            return true // Indicate the event was handled
        }
        return super.onKeyUp(keyCode, event)
    }
    private val pathPaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 50f
        isAntiAlias = true
    }

    private val path = Path()
    private val texts = mutableListOf<Pair<String, Pair<Float, Float>>>()

    private var typingEnabled = false
    private var currentText = StringBuilder()
    private var cursorPosition: Pair<Float, Float>? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val toolType = event.getToolType(0)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (toolType) {
                    MotionEvent.TOOL_TYPE_MOUSE -> {
                        // Enable text input on mouse click
                        cursorPosition = Pair(x, y)
                        typingEnabled = true
                        invalidate()
                    }
                    MotionEvent.TOOL_TYPE_FINGER, MotionEvent.TOOL_TYPE_STYLUS -> {
                        // Enable drawing
                        typingEnabled = false
                        path.moveTo(x, y)
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (toolType == MotionEvent.TOOL_TYPE_FINGER || toolType == MotionEvent.TOOL_TYPE_STYLUS) {
                    path.lineTo(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                // Optional: Do something when touch is lifted
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the drawing paths
        canvas.drawPath(path, pathPaint)

        // Draw the existing text
        texts.forEach { (text, position) ->
            canvas.drawText(text, position.first, position.second, textPaint)
        }

        // Draw the current typing text
        cursorPosition?.let { position ->
            if (typingEnabled && currentText.isNotEmpty()) {
                canvas.drawText(currentText.toString(), position.first, position.second, textPaint)
            }
        }
    }

    fun addTypedText(keyCode: Int) {
        if (typingEnabled) {
            when (keyCode) {
                android.view.KeyEvent.KEYCODE_DEL -> {
                    // Handle backspace
                    if (currentText.isNotEmpty()) {
                        currentText.deleteCharAt(currentText.length - 1)
                    }
                }
                else -> {
                    val char = android.view.KeyEvent.keyCodeToString(keyCode).replace("KEYCODE_", "").firstOrNull()
                    if (char != null) {
                        currentText.append(char)
                    }
                }
            }
            invalidate()
        }
    }

    fun finishTyping() {
        if (typingEnabled && currentText.isNotEmpty() && cursorPosition != null) {
            texts.add(currentText.toString() to cursorPosition!!)
            currentText.clear()
            typingEnabled = false
            cursorPosition = null
            invalidate()
        }
    }

    fun clearCanvas() {
        path.reset()
        texts.clear()
        currentText.clear()
        typingEnabled = false
        cursorPosition = null
        invalidate()
    }

//    private fun showPenSizeChooserDialog() {
//        val penDialog = Dialog(this)
//        penDialog.setContentView(R.layout.dialog_pen_size)
//        penDialog.setTitle("Pen size: ")
//        val smallBtn = penDialog.findViewById<View>(R.id.ib_small_pen)
//        val mediumBtn = penDialog.findViewById<View>(R.id.ib_medium_pen)
//        val largeBtn = penDialog.findViewById<View>(R.id.ib_large_pen)
//        // Small size Pen
//        smallBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(10.toFloat())
//            penDialog.dismiss()
//        }
//        // Medium size Pen
//        mediumBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(20.toFloat())
//            penDialog.dismiss()
//        }
//        // Large size pen
//        largeBtn?.setOnClickListener {
//            drawingView?.setSizeForPen(30.toFloat())
//            penDialog.dismiss()
//        }
//        // Shows pen dialog
//        penDialog.show()
//    }
//
//    fun colorClicked(view: View){
//        if(view !== mImageButtonCurrentPaint){
//            val imageButton = view as ImageButton
//            val colorTag = imageButton.tag.toString()
//            drawingView?.setColor(colorTag)
//
//            imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
//
//            )
//            mImageButtonCurrentPaint?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal)
//
//            )
//
//
//            mImageButtonCurrentPaint = view
//
//        }
//    }
}
