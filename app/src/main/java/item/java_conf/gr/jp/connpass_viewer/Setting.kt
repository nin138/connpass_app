package item.java_conf.gr.jp.connpass_viewer

import android.content.Context


object Setting {
  enum class SearchRange(var days: Int) {
    ONE_WEEK(7),
    TWO_WEEK(14),
    ONE_MONTH(30),
    UNLIMITED(0)
  }
  val settings = "settings"

  var userName: String? = null
    get() = field
    private set
  var searchRange: Int = 0
    get() = field
    private set
  var isOr: Boolean = false
    get() = field
    private set

  fun init(context: Context) {
    val prefs = context.getSharedPreferences(Setting.settings, Context.MODE_PRIVATE)
    this.userName = prefs.getString("userName", null)
    this.searchRange = prefs.getInt("searchRange", 0)
    this.isOr = prefs.getBoolean("isOr", false)
  }
  fun set(context: Context, userName: String, searchRange: SearchRange, isOr: Boolean) {
    this.userName = userName
    this.searchRange = searchRange.days
    this.isOr = isOr
    val editor = context.getSharedPreferences(Setting.settings, Context.MODE_PRIVATE).edit()
    editor.putString("userName", this.userName)
    editor.putBoolean("isOr", this.isOr)
    editor.putInt("searchRange", this.searchRange)
    editor.apply()
  }
}
