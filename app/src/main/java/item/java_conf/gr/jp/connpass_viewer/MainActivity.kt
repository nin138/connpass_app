package item.java_conf.gr.jp.connpass_viewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassResponse
import item.java_conf.gr.jp.connpass_viewer.entity.Event
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val arr: Array<Event> = arrayOf()
    val adaptor = RecyclerAdapter(this, arr)
    adaptor.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
      override fun onItemClick(adapter: RecyclerAdapter, position: Int, event: Event) {
        Toast.makeText(this@MainActivity, event.title, Toast.LENGTH_SHORT).show()
      }
    })
//    val layoutManager = LinearLayoutManager(this)
//    recycler_view.layoutManager = layoutManager
//    recycler_view.adapter = adaptor
//
//    val connpassUrl = "https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=2"
//    test.setOnClickListener {
//      val http = Http()
//      http.setCallback(object : Http.Callback {
//        override fun onSuccess(body: ConnpassResponse) {
//          adaptor.setList(body.events)
//        }
//        override fun onError() {
//          Toast.makeText(this@MainActivity, "net connection error", Toast.LENGTH_SHORT).show()
//        }
//      })
//      http.execute(connpassUrl)
//    }
  }
}
