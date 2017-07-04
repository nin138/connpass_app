package item.java_conf.gr.jp.connpass_viewer

import android.os.Bundle
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassResponse
import item.java_conf.gr.jp.connpass_viewer.entity.Event
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.nav_header_top.*

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_top)
    val toolbar = findViewById(R.id.toolbar) as Toolbar

    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
    val toggle = ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    val navigationView = findViewById(R.id.nav_view) as NavigationView
    navigationView.setNavigationItemSelectedListener(this)

    //////
    val arr: Array<Event> = arrayOf()
    val adaptor = RecyclerAdapter(this, arr)
    adaptor.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
      override fun onItemClick(adapter: RecyclerAdapter, position: Int, event: Event) {
        Toast.makeText(this@TopActivity, event.title, Toast.LENGTH_SHORT).show()
      }
    })

    val layoutManager = LinearLayoutManager(this)
    recycler_view.layoutManager = layoutManager
    recycler_view.adapter = adaptor

    val connpassUrl = "https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=2"

    fun updateList(url: String) {
      val http = Http()
      http.setCallback(object : Http.Callback {
        override fun onSuccess(body: ConnpassResponse) {
          adaptor.setList(body.events)
        }
        override fun onError() {
          Toast.makeText(this@TopActivity, "net connection error", Toast.LENGTH_SHORT).show()
        }
      })
      http.execute(url)
    }
    button.setOnClickListener(object : View.OnClickListener {
      override fun onClick(p0: View?) {
        updateList(connpassUrl)
      }
    })


    nav_view.getHeaderView(0).findViewById<View>(R.id.simpleSearch).setOnClickListener {
      Toast.makeText(this@TopActivity, "test", Toast.LENGTH_SHORT).show()
    }


  }

  override fun onBackPressed() {
    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }


  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    val id = item.itemId

    if (id == R.id.nav_my_event) {
      // Handle the camera action
    } else if (id == R.id.nav_advanced_search) {

    } else if (id == R.id.nav_favorite) {

    } else if (id == R.id.nav_black_list) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
    drawer.closeDrawer(GravityCompat.START)
    return true
  }
}
