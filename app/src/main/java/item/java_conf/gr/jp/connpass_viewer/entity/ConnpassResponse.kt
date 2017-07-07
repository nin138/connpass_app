package item.java_conf.gr.jp.connpass_viewer.entity

import java.io.Serializable

data class ConnpassResponse(
        var results_returned: Int,//	整数	含まれる検索結果の件数	1
        var results_available: Int, //	整数	検索結果の総件数	191
        var results_start: Int, //	整数	検索の開始位置	1
        var events: Array<Event>//	配列(複数要素)	検索結果のイベントリスト
)
data class Event(
        var event_id: Int, //	整数	イベントID	364
        var title: String, //	文字列(UTF-8)	タイトル	BPStudy#56
        var catch: String, //	文字列(UTF-8)	キャッチ	株式会社ビープラウドが主催するWeb系技術討論の会
        var description: String, //	文字列(UTF-8)	概要(HTML形式)	今回は「Python プロフェッショナル　プログラミング」執筆プロジェクトの継続的ビルドについて、お話しして頂きます。
        var event_url: String, //	文字列(UTF-8)	connpass.com 上のURL	https://connpass.com/event/364/
        var hash_tag: String, //	文字列(UTF-8)	Twitterのハッシュタグ	bpstudy
        var started_at: String, //	文字列(UTF-8)	イベント開催日時 (ISO-8601形式)	2012-04-17T18:30:00+09:00
        var ended_at: String, //	文字列(UTF-8)	イベント終了日時 (ISO-8601形式)	2012-04-17T20:30:00+09:00
        var limit: Int, //	整数	定員	80
        var event_type: String, //	文字列(UTF-8)	イベント参加タイプ	participation: connpassで参加受付あり,advertisement: 告知のみ
        var series: Series?, //	オブジェクト	グループ
        var address: String, //	文字列(UTF-8)	開催場所	東京都渋谷区千駄ヶ谷5-32-7
        var place: String, //	文字列(UTF-8)	開催会場	BPオフィス (NOF南新宿ビル4F)
        var lat: Double, //	浮動小数点数	開催会場の緯度	35.680236100000
        var lon: Double, //	浮動小数点数	開催会場の経度	139.701308500000
        var owner_id: Int, //	整数	管理者のID	8
        var owner_nickname: String, //	文字列(UTF-8)	管理者のニックネーム	haru860
        var owner_display_name: String, //	文字列(UTF-8)	管理者の表示名	佐藤 治夫
        var accepted: Int, //	整数	参加者数	80
        var waiting: Int, //	整数	補欠者数	15
        var updated_at: String
)
class SerializableEvent(e: Event): Serializable {
  val event_id = e.event_id
  val title = e.title
  val catch = e.catch
  val description = e.description
  val event_url = e.event_url
  val hash_tag = e.hash_tag
  val started_at = e.started_at
  val ended_at = e.ended_at
  val limit = e.limit
  val event_type = e.event_type
  val address = e.address
  val place = e.place
  val lat = e.lat
  val lon = e.lon
  val owner_id = e.owner_id
  val owner_nickname = e.owner_nickname
  val owner_display_name = e.owner_display_name
  val accepted = e.accepted
  val waiting = e.waiting
  val updated_at = e.updated_at
  val series_id = e.series?.id
  val series_title = e.series?.title
  val series_url = e.series?.url
}
data class Series(
        var id: Int, //	整数	グループID	1
        var title: String, //	文字列(UTF-8)	グループタイトル	BPStudy
        var url: String //文字列(UTF-8)	グループのconnpass.com 上のURL	https://connpass.com/series/1/
)
