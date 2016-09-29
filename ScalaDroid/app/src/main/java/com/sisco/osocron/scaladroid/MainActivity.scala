package com.sisco.osocron.scaladroid

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.View.OnClickListener
import com.sisco.osocron.scaladroid.MyActivity._

class MainActivity extends AppCompatActivity {

  override protected def onCreate(savedInstanceState: Bundle) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar = this.findView[Toolbar](R.id.toolbar)
    setSupportActionBar(toolbar)

    val fab = this.findView[FloatingActionButton](R.id.fab)
    fab.click((view: View) =>
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    )

  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.menu_main, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    val id: Int = item.getItemId
    if (id == R.id.action_settings) {
      return true
    }
    super.onOptionsItemSelected(item)
  }
}

object MyActivity {

  implicit class PimpMyView(val view: View) extends AnyVal {

    def click(f: (View) ⇒ Unit) = view.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = f(v)
    })
  }

  implicit class PimpMyActivity(val a: Activity) extends AnyVal {
    /**
      * Generic Type method to deal with annoying conversions for views
      *
      * @param id - the Resource ID for the view in question
      * @tparam T - the type associated with the widget (Button, TextView, etc)
      * @return the found view with the desired type
      */
    def findView[T](id: Int): T = {
      a.findViewById(id).asInstanceOf[T]
    }
  }

}