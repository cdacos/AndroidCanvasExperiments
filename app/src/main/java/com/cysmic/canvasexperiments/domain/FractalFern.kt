package com.cysmic.canvasexperiments.domain

import android.graphics.PointF
import com.cysmic.canvasexperiments.ui.SketchHandler

class FractalFern(chosenMatrix: Int) : SketchHandler {
  data class FractalFernMatrix(
      val name: String,
      val scale: Float,
      val f1: ArrayList<Float>,
      val f2: ArrayList<Float>,
      val f3: ArrayList<Float>,
      val f4: ArrayList<Float>)

  companion object {
    val fractals = arrayListOf(
        FractalFernMatrix("Barnsley's", 1.0f,
            arrayListOf(0f, 0f, 0f, 0.16f, 0f, 0f, 0.01f),
            arrayListOf(0.85f, 0.04f, -0.04f, 0.85f, 0f, 1.6f, 0.85f),
            arrayListOf(0.2f, -0.26f, 0.23f, 0.22f, 0f, 1.6f, 0.07f),
            arrayListOf(-0.16f, 0.28f, 0.26f, 0.24f, 0f, 0.44f, 0.07f)),

        FractalFernMatrix("Culcita", 1.0f,
            arrayListOf(0f, 0f, 0f, 0.25f, 0f, -0.14f, 0.02f),
            arrayListOf(0.85f, 0.02f, -0.02f, 0.83f, 0f, 1f, 0.84f),
            arrayListOf(0.09f, -0.28f, 0.3f, 0.11f, 0f, 0.6f, 0.07f),
            arrayListOf(-0.09f, 0.28f, 0.3f, 0.09f, 0f, 0.7f, 0.07f)),

        FractalFernMatrix("'Fishbone'", 1.0f,
            arrayListOf(0f, 0f, 0f, 0.25f, 0f, -0.4f, 0.02f),
            arrayListOf(0.95f, 0.002f, -0.002f, 0.93f, -0.002f, 0.5f, 0.84f),
            arrayListOf(0.035f, -0.11f, 0.27f, 0.01f, -0.05f, 0.005f, 0.07f),
            arrayListOf(-0.04f, 0.11f, 0.27f, 0.01f, 0.047f, 0.06f, 0.07f)),

        FractalFernMatrix("Cyclosorus", 1.0f,
            arrayListOf(0f, 0f, 0f, 0.25f, 0f, -0.4f, 0.02f),
            arrayListOf(0.95f, 0.005f, -0.005f, 0.93f, -0.002f, 0.5f, 0.84f),
            arrayListOf(0.035f, -0.2f, 0.16f, 0.04f, -0.09f, 0.02f, 0.07f),
            arrayListOf(-0.04f, 0.2f, 0.16f, 0.04f, 0.083f, 0.12f, 0.07f))
    )
  }

  private val matrix = fractals[chosenMatrix]
  private var point = PointF(0f, 0f)
  private var index = 0L

  private fun nextPoint(f: ArrayList<Float>, p: PointF, scale: Float = 1f): PointF {
    val x = f[0] * p.x + f[1] * p.y + f[4]
    val y = f[2] * p.x + f[3] * p.y + f[5]
    return PointF(x * scale, y * scale)
  }

  override fun reset() {
    point = PointF(0f, 0f)
    index = 0
  }

  override fun drawPoint(): PointF? {
    if (index < 500_000) {
      val rnd = Math.random()
      point = when {
        rnd <= matrix.f1[6] -> {
          nextPoint(matrix.f1, point)
        }
        rnd <= (matrix.f1[6] + matrix.f2[6]) -> {
          nextPoint(matrix.f2, point)
        }
        rnd <= (1 - matrix.f4[6]) -> {
          nextPoint(matrix.f3, point)
        }
        else -> {
          nextPoint(matrix.f4, point)
        }
      }
      index++
      return point
    }
    return null
  }
}