package com.cysmic.canvasexperiments.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.cysmic.canvasexperiments.R
import com.cysmic.canvasexperiments.domain.FractalFern
import kotlinx.android.synthetic.main.activity_fractal_fern.*

class SketchActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fractal_fern)

    val actionBar = supportActionBar
    if (actionBar != null) {
      actionBar.title = resources.getString(R.string.fractal_ferns)
      actionBar.subtitle = resources.getString(R.string.fractal_ferns_subtitle, intent.getStringExtra("TITLE"))
      actionBar.setDisplayHomeAsUpEnabled(true)
    }

    sketch.handler = FractalFern(intent.getIntExtra("INDEX", 0))
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) finish()
    return super.onOptionsItemSelected(item)
  }
}

