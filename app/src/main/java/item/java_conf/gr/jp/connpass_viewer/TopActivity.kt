package item.java_conf.gr.jp.connpass_viewer

import android.Manifest
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
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
import android.widget.EditText
import item.java_conf.gr.jp.connpass_viewer.fragment.RecyclerFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.AdvancedSearchFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.SettingFragment
import item.java_conf.gr.jp.connpass_viewer.fragment.BlackListFragment
import kotlinx.android.synthetic.main.activity_top.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget



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
            .setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->   })
            .create()
            .show()
        this.requestPermissions(arrayOf(Manifest.permission.INTERNET), PERMISSION_REQUEST_INTERNET)
      }
    }

    val db = SQLite(this)
    val l = db.getBlackList()
    for(s in l) {
      System.out.println(s.title)
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
      Setting.simpleRequest.keyword = nav_view.getHeaderView(0).findViewById<EditText>(R.id.simpleSearchEditText).text.split(" ")
      changeFragment(RecyclerFragment(Setting.simpleRequest))
      val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      if (currentFocus != null) inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
      if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START)
      Setting.simpleRequest.start = 1
      Setting.simpleRequest.finished = false

    }

    val target = GlideDrawableImageViewTarget(gifView)
    Glide.with(this).load(R.raw.loader).into(target)
    gifView.visibility = View.INVISIBLE

    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_frame, RecyclerFragment(Setting.getNewEventRequest()))
    transaction.addToBackStack(null)
    transaction.commit()
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

    when(id) {
      R.id.nav_my_event -> toMyEvent()
      R.id.nav_new_event -> changeFragment(RecyclerFragment(Setting.getNewEventRequest()))
      R.id.nav_advanced_search -> changeFragment(AdvancedSearchFragment())
      R.id.nav_black_list -> changeFragment(BlackListFragment())
      R.id.nav_setting -> changeFragment(SettingFragment())
      R.id.nav_favorite -> Toast.makeText(this, "未実装", Toast.LENGTH_SHORT).show() //TODO impl
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
  fun toMyEvent() {
    if(Setting.userName == null || Setting.userName == "") {
      val builder: AlertDialog.Builder = AlertDialog.Builder(this@TopActivity)
      builder.setMessage("マイイベントを見るにはユーザー名の登録が必要です。")
          .setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->  changeFragment(SettingFragment()) })
          .create()
          .show()
    } else {
      Setting.myEventRequest.start = 1
      Setting.myEventRequest.finished = false
      changeFragment(RecyclerFragment(Setting.myEventRequest))
    }
  }
}
