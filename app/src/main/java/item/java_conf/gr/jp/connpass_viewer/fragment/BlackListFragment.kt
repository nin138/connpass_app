package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.SQLite
import item.java_conf.gr.jp.connpass_viewer.entity.Series
import kotlinx.android.synthetic.main.blacklist_fragment.*

class BlackListFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return inflater.inflate(R.layout.blacklist_fragment, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val db = SQLite(activity)
    val adapter = ArrayAdapter<Series>(activity, android.R.layout.simple_list_item_1, db.getBlackList())
    list_view.adapter = adapter
    if(adapter.count == 0) {
      val alert = AlertDialog.Builder(activity)
      alert.setMessage("イベントをスワイプすることでグループをブラックリストに設定できます。")
          .setPositiveButton("ok", null)
          .setCancelable(true)
          .create()
          .show()
    }
    list_view.onItemClickListener = object : AdapterView.OnItemClickListener {
      override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(adapter.getItem(p2).title + "のブラックリスト設定を解除しますか？")
            .setPositiveButton("ok", DialogInterface.OnClickListener { _, _ ->
              val sqlite = SQLite(activity)
              sqlite.removeBlackList(adapter.getItem(p2).id)
              adapter.remove(adapter.getItem(p2))
            })
            .setNegativeButton("cancel", null)
            .setCancelable(true)
            .create()
            .show()
      }
    }
  }
}
