package item.java_conf.gr.jp.connpass_viewer

import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.fragment.RecyclerFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.AdvancedSearchFragment
import kotlinx.android.synthetic.main.activity_top.*

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


    nav_view.getHeaderView(0).findViewById<View>(R.id.simpleSearch).setOnClickListener {
      Toast.makeText(this@TopActivity, "test", Toast.LENGTH_SHORT).show()
      changeFragment(RecyclerFragment())
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
      changeFragment(RecyclerFragment())
    } else if (id == R.id.nav_advanced_search) {
      changeFragment(AdvancedSearchFragment())
    } else if (id == R.id.nav_favorite) {
      changeFragment(RecyclerFragment())
    } else if (id == R.id.nav_black_list) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
    drawer.closeDrawer(GravityCompat.START)
    return true
  }
  fun changeFragment(fragment: Fragment) {
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_frame, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}
