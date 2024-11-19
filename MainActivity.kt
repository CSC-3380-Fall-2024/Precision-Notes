package com.example.inks_and_pens

// Be sure to add the below imports
import android.os.Bundle
import  android.app.Dialog
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get

//

class MainActivity : ComponentActivity() {
    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForPen(20.toFloat())

        val linearLayoutPenColors = findViewById<LinearLayout>(R.id.ll_paint_colors)

        mImageButtonCurrentPaint = linearLayoutPenColors.findViewById(R.id.ib_yellow)
        mImageButtonCurrentPaint?.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )

        val ibPen = findViewById<ImageButton>(R.id.ib_brush)
        ibPen.setOnClickListener {
            showPenSizeChooserDialog()
        }

        val ibUndo = findViewById<ImageButton>(R.id.ib_undo)
        ibUndo.setOnClickListener {
        drawingView?.onClickUndo()
        }

        val ibRedo = findViewById<ImageButton>(R.id.ib_redo)
        ibRedo.setOnClickListener {
            drawingView?.onClickRedo()
        }
    }

    private fun showPenSizeChooserDialog() {
        val penDialog = Dialog(this)
        penDialog.setContentView(R.layout.dialog_pen_size)
        penDialog.setTitle("Pen size: ")
        val smallBtn = penDialog.findViewById<View>(R.id.ib_small_pen)
        val mediumBtn = penDialog.findViewById<View>(R.id.ib_medium_pen)
        val largeBtn = penDialog.findViewById<View>(R.id.ib_large_pen)
        // Small size Pen
        smallBtn?.setOnClickListener {
            drawingView?.setSizeForPen(10.toFloat())
            penDialog.dismiss()
        }
        // Medium size Pen
        mediumBtn?.setOnClickListener {
            drawingView?.setSizeForPen(20.toFloat())
            penDialog.dismiss()
        }
        // Large size pen
        largeBtn?.setOnClickListener {
            drawingView?.setSizeForPen(30.toFloat())
            penDialog.dismiss()
        }
        // Shows pen dialog
        penDialog.show()
    }

    fun colorClicked(view: View){
        if(view !== mImageButtonCurrentPaint){
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)

           imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed)

            )
            mImageButtonCurrentPaint?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_normal)

            )


            mImageButtonCurrentPaint = view

        }
    }
}