package item.java_conf.gr.jp.connpass_viewer.entity

class ConnpassQuery(
    var event_id: Array<Int>?,
    var keyword: Array<String>?,
    var keyword_is_or: Boolean = false,
    var ym: Array<String>?, // yyyymm
    var ymd: Array<String>?, // yyyymmdd
    var nickname: Array<String>?,
    var owner_nickname: Array<String>?,
    var series_id: Array<Int>?,
    var start: Int = 1,
    var order: Int = 2   //1: 更新日時順,2: 開催日時順,3: 新着順
) {
  private val baseUrl = "https://connpass.com/api/v1/event/?"
  fun getQuery(): String {
    return baseUrl +
        getQueryAttr(event_id, "event_id")+
        getQueryAttr(keyword, if(!keyword_is_or) "keyword=" else "keyword_or=") +
        getQueryAttr(ym, "ym") +
        getQueryAttr(ymd, "ymd") +
        getQueryAttr(nickname, "nickname") +
        getQueryAttr(owner_nickname, "owner_nickname") +
        getQueryAttr(series_id, "series_id") +
        if(start != 1) "start=" + start.toString() + "&" else {""} +
        "order=" + order.toString() + "&" +
        "count=20"
  }

  private fun getQueryAttr(array: Array<String>?, name: String): String {
    if(array != null) {
      var query = name + "="
      array.forEach { query += it + "," }
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