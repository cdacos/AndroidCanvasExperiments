package com.cysmic.canvasexperiments.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Fractal Ferns - http://www.home.aone.net.au/~byzantium/ferns/fractal.html
 */
class SketchView : SurfaceView, SurfaceHolder.Callback {
  private var thread: WhiteboardThread? = null

  lateinit var handler: SketchHandler

  constructor(context: Context) : super(context) { setup() }

  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) { setup() }

  constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) { setup() }

  private fun setup() {
    holder.addCallback(this)
  }

  override fun surfaceCreated(holder: SurfaceHolder?) {
    if (holder != null) {
      thread = WhiteboardThread(holder, handler)
      thread?.start()
    }
  }

  override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) { }

  override fun surfaceDestroyed(holder: SurfaceHolder?) {
    while (true) {
      try {
        thread?.isRunning = false
        thread?.join()
        thread = null
        break
      }
      catch (ignored: Exception) { }
    }
  }

  private class WhiteboardThread(private val holder: SurfaceHolder, private val handler: SketchHandler) : Thread() {
    private val paint = Paint()
    private var offsetX = 0f
    private var offsetY = 0f
    private var pixelScale = 0f

    @Volatile var isRunning = false

    init {
      paint.alpha = 255
      paint.color = Color.rgb(30, 200, 60)
    }

    override fun run() {
      offsetX = holder.surfaceFrame?.exactCenterX() ?: 0f
      offsetY = (holder.surfaceFrame?.height()?.toFloat() ?: 0f) * 0.9f
      pixelScale = offsetX / 5
      isRunning = true

      var canvas: Canvas? = null

      while(isRunning) {
        try {
          canvas = holder.lockCanvas()
          synchronized(holder) {
            handler.reset()
            canvas.drawColor(Color.BLACK)
            var p = handler.drawPoint()
            while (p != null) {
              drawPoint(canvas, p)
              p = handler.drawPoint()
            }
          }
          isRunning = false
        }
        catch (e: Exception) {
          e.printStackTrace()
        }
        finally {
          if (canvas != null) holder.unlockCanvasAndPost(canvas)
        }
      }
      isRunning = false
    }

    private fun drawPoint(canvas: Canvas, p: PointF) {
      val x = offsetX + (p.x * pixelScale)
      val y = offsetY - (p.y * pixelScale)
      canvas.drawPoint(x, y, paint)
    }
  }
}