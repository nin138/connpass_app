package item.java_conf.gr.jp.connpass_viewer

import android.content.Context
import android.util.Log
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest


object Setting {
  private val preferenceName = "settings"

  var userName: String? = null
    get() = field
    private set
  var searchRange = ConnpassRequest.SearchRange.UNLIMITED
    get() = field
    private set
  var isOr: Boolean = false
    get() = field
    private set

  val blackList = ArrayList<Int>()

  var encording: String? = null
  var myEventRequest: ConnpassRequest = ConnpassRequest()
  val simpleRequest: ConnpassRequest = ConnpassRequest()

  fun init(context: Context) {
    val prefs = context.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE)
    this.userName = prefs.getString("userName", null)
    val int = prefs.getInt("searchRange", 0)
    this.searchRange = ConnpassRequest.SearchRange.values().filter { int == it.days }.first()
    this.isOr = prefs.getBoolean("isOr", false)
    this.onStateChange()
    val db = SQLite(context)
    db.getBlackList().forEach {
      Log.d("id", it.id.toString())
      blackList.add(it.id)
    }
  }
  fun set(context: Context, userName: String, searchRange: ConnpassRequest.SearchRange, isOr: Boolean) {
    this.userName = userName
    this.searchRange = searchRange
    this.isOr = isOr
    val editor = context.getSharedPreferences(this.preferenceName, Context.MODE_PRIVATE).edit()
    editor.putString("userName", this.userName)
    editor.putBoolean("isOr", this.isOr)
    editor.putInt("searchRange", this.searchRange.days)
    editor.apply()
    this.onStateChange()
  }
  private fun onStateChange() {
    if(this.userName != null) {
      myEventRequest.nickname = listOf(this.userName!!)
    }
    simpleRequest.date_range = searchRange
    simpleRequest.keyword_is_or = isOr
  }
  fun getNewEventRequest(): ConnpassRequest {
    val req = ConnpassRequest()
    req.order = 3
    return req
  }
}
