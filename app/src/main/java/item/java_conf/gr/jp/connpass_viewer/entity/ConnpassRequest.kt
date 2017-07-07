package item.java_conf.gr.jp.connpass_viewer.entity

import java.util.*

class ConnpassRequest {
  enum class SearchRange(var days: Int) {
    ONE_WEEK(7),
    TWO_WEEK(14),
    ONE_MONTH(30),
    UNLIMITED(0)
  }

  var event_id: Array<Int>? = null
  var keyword: List<String>? = null
  var keyword_is_or: Boolean = false
  var ym: Array<String>? = null// yyyymm
  var ymd: Array<String>? = null // yyyymmdd
  var date_range: SearchRange = SearchRange.UNLIMITED
  var nickname: List<String>? =null
  var owner_nickname: Array<String>? = null
  var series_id: Array<Int>? = null
  var start: Int = 1
  var order: Int = 2   //1: 更新日時順,2: 開催日時順,3: 新着順

  private val baseUrl = "https://connpass.com/api/v1/event/?"
  fun getQuery(): String {
    return baseUrl +
        getQueryAttr(event_id, "event_id")+
        getQueryAttr(keyword, if(!keyword_is_or) "keyword=" else "keyword_or=") +
//        getQueryAttr(ym, "ym") +
//        getQueryAttr(ymd, "ymd") +
        getDateQuery() +
        getQueryAttr(nickname, "nickname") +
        getQueryAttr(owner_nickname, "owner_nickname") +
        getQueryAttr(series_id, "series_id") +
        if(start != 1) "start=" + start.toString() + "&" else {""} +
        "order=" + order.toString() + "&" +
        "count=20"
  }
  private fun getDateQuery(): String {
    fun getDateRange(range: SearchRange, start: Calendar = Calendar.getInstance()): List<String>? {
      if(range == SearchRange.UNLIMITED) return null
        fun getDateString(cal: Calendar): String {
          val month_ = "0" + cal.get(Calendar.MONTH).toString()
          val day_ = "0" + cal.get(Calendar.DAY_OF_MONTH).toString()
          return cal.get(Calendar.YEAR).toString() + month_.slice(month_.length-2..month_.length-1) + day_.slice(day_.length-2..day_.length-1)
        }
        val list: MutableList<String> = mutableListOf()
        for(i in 0..range.days) {
          list.add(getDateString(start))
          start.add(Calendar.DAY_OF_MONTH, 1)
        }
      return list
    }
    if(date_range == SearchRange.UNLIMITED) return ""
    return getQueryAttr(getDateRange(date_range), "ymd")
  }

  private fun getQueryAttr(array: List<String>?, name: String): String {
    if(array != null) {
      var query = name + "="
      array.forEach { if(it != "")query += it + "," }
      if (query.last() == ',') query.substring(0, query.length - 1)
      return query + "&"
    }
    return ""
  }
  private fun getQueryAttr(array: Array<String>?, name: String): String {
    if(array != null) {
      var query = name + "="
      array.forEach { if(it != "")query += it + "," }
      if (query.last() == ',') query.substring(0, query.length - 1)
      return query + "&"
    }
    return ""
  }
  private fun getQueryAttr(array: Array<Int>?, name: String): String {
    if(array != null) {
      var query = name + "="
      array.forEach { query += it.toString() + "," }
      if (query.last() == ',') query.substring(0, query.length - 1)
      return query + "&"
    }
    return ""
  }
}