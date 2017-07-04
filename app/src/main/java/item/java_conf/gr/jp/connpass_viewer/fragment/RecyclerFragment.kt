package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.Http
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.RecyclerAdapter
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassResponse
import item.java_conf.gr.jp.connpass_viewer.entity.Event
import kotlinx.android.synthetic.main.recycler_fragment.*


class RecyclerFragment() : Fragment() {
  var request: ConnpassRequest? = null
  constructor(request: ConnpassRequest): this() {
    this.request = request
  }
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return inflater.inflate(R.layout.recycler_fragment, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val arr: Array<Event> = arrayOf()
    val adaptor = RecyclerAdapter(activity, arr)
    adaptor.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
      override fun onItemClick(adapter: RecyclerAdapter, position: Int, event: Event) {
        Toast.makeText(activity, event.title, Toast.LENGTH_SHORT).show()
      }
    })

    val layoutManager = LinearLayoutManager(activity)
    recycler_view.layoutManager = layoutManager
    recycler_view.adapter = adaptor

    val connpassUrl = "https://connpass.com/api/v1/event/?nickname=yanokunpei&count=20&order=2"

    fun updateList(url: String) {
      val http = Http()
      http.setCallback(object : Http.Callback {
        override fun onSuccess(body: ConnpassResponse) {
          adaptor.setList(body.events)
        }
        override fun onError() {
          Toast.makeText(activity, "net connection error", Toast.LENGTH_SHORT).show()
        }
      })
      http.execute(url)
    }
    updateList(connpassUrl)
  }
}