package item.java_conf.gr.jp.connpass_viewer

import android.content.Context
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest


object Setting {
  private val preferenceName = "settings"

  var userName: String? = null
    get() = field
    private set
  var searchRange: Int = 0
    get() = field
    private set
  var isOr: Boolean = false
    get() = field
    private set

  val myEventRequest: ConnpassRequest = ConnpassRequest()
  val simpleReqest: ConnpassRequest = ConnpassRequest()

  fun init(context: Context) {
    val prefs = context.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE)
    this.userName = prefs.getString("userName", null)
    this.searchRange = prefs.getInt("searchRange", 0)
    this.isOr = prefs.getBoolean("isOr", false)
    this.onStateChange()
  }
  fun set(context: Context, userName: String, searchRange: ConnpassRequest.SearchRange, isOr: Boolean) {
    this.userName = userName
    this.searchRange = searchRange.days
    this.isOr = isOr
    val editor = context.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE).edit()
    editor.putString("userName", this.userName)
    editor.putBoolean("isOr", this.isOr)
    editor.putInt("searchRange", this.searchRange)
    editor.apply()
    this.onStateChange()
  }
  private fun onStateChange() {
    if(this.userName != null) {
      myEventRequest.nickname = listOf(this.userName!!)
    }
  }
}
