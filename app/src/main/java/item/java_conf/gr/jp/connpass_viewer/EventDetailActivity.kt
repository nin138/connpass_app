package item.java_conf.gr.jp.connpass_viewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import item.java_conf.gr.jp.connpass_viewer.entity.SerializableEvent
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_detail)
    val event = intent.getSerializableExtra("event") as SerializableEvent
//    web_view.settings.useWideViewPort = true
//    web_view.settings.loadWithOverviewMode = true
    val str = """<head>
    <meta name="viewport" content="initial-scale=1.0">
    <style type="text/css">
    body {
      width: 90vw;
      margin: 0 auto;
      overflow-x: hidden;
    }
    * {
      word-break: break-all;
      max-width: 90vw;
    }
    </style>
    </head>
    <body>
    <div>
    <span>${event.started_at.substring(5, 7) + "/" + event.started_at.substring(8, 10)}</span>
    <h1>${event.title}</h1>
    <p>${event.catch}</p>
    <p>場所: ${event.address}  ${event.place}</p>
    </div>
    <p>${event.started_at.substring(11, 16)}~${event.ended_at.substring(11, 16)}</p>
    <p${if(event.waiting != 0) " style=\"color:red;\"" else ""}>${event.accepted+event.waiting}/${event.limit}人</p>
    <p>ハッシュタグ: ${event.hash_tag}</p>
    <h2>イベントの説明</h2>"""
    web_view.loadData(str + event.description + "</body>", "text/html;charset=" + Setting.encording, Setting.encording)
  }
}
