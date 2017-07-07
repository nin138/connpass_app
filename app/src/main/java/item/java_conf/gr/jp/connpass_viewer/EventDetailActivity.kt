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
//    web_view.settings.loadWithOverviewMode = true
//    web_view.settings.useWideViewPort = true
    web_view.loadData(event.description, "text/html", "shift-jis")
  }
}
