package com.cysmic.canvasexperiments.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.cysmic.canvasexperiments.R
import com.cysmic.canvasexperiments.domain.FractalFern
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val fernFractalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
    fernFractalAdapter.addAll(FractalFern.fractals.map { m -> m.name })
    fractal_ferns_list.adapter = fernFractalAdapter

    fractal_ferns_button.setOnClickListener {
      val intent = Intent(this, SketchActivity::class.java)
      intent.putExtra("TYPE", "FractalFern")
      intent.putExtra("TITLE", resources.getString(R.string.fractal_ferns))
      intent.putExtra("SUB_TITLE", fractal_ferns_list.selectedItem.toString())
      intent.putExtra("INDEX", fractal_ferns_list.selectedItemPosition)
      startActivity(intent)
    }
  }
}
