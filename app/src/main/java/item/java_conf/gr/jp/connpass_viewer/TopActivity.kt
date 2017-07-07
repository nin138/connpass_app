package item.java_conf.gr.jp.connpass_viewer

import android.Manifest
import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.content.PermissionChecker
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.fragment.RecyclerFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.AdvancedSearchFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  val PERMISSION_REQUEST_INTERNET = 122


  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if(requestCode == PERMISSION_REQUEST_INTERNET && grantResults.isNotEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
      finish()
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Setting.init(this)
    setContentView(R.layout.activity_top)
    if(Build.VERSION.SDK_INT >= 23) {
      if(PermissionChecker.checkSelfPermission(this@TopActivity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@TopActivity)
        builder.setMessage("このアプリはconnpass APIを利用するためインターネットへのアクセスを必要とします。そのためインターネットアクセスの許可が得られなかった場合終了します。")
            .setPositiveButton("ok", DialogInterface.OnClickListener { dialogInterface, i ->   })
            .create()
            .show()
        this.requestPermissions(arrayOf(Manifest.permission.INTERNET), PERMISSION_REQUEST_INTERNET)
      }
    }

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
      changeFragment(RecyclerFragment("https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=3"))
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
    val id = item.itemId

    if (id == R.id.nav_my_event) {
      changeFragment(RecyclerFragment("https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=2"))
    } else if (id == R.id.nav_advanced_search) {
      changeFragment(AdvancedSearchFragment())
    } else if (id == R.id.nav_favorite) {
      changeFragment(RecyclerFragment("https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=1"))
    } else if (id == R.id.nav_black_list) {

    } else if (id == R.id.nav_setting) {
      changeFragment(SettingFragment())

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
