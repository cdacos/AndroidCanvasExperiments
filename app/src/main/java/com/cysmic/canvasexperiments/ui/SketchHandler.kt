package com.cysmic.canvasexperiments.ui

import android.graphics.PointF

interface SketchHandler {
  fun reset()
  fun drawPoint() : PointF? // Return null when finished drawing
}