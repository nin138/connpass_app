package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest
import kotlinx.android.synthetic.main.advanced_search_fragment.*
import kotlinx.android.synthetic.main.list_item.*

class AdvancedSearchFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return inflater.inflate(R.layout.advanced_search_fragment, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    search_btn.setOnClickListener {
      val request = ConnpassRequest()
      request.keyword = keywords_edit.text.trim().split(" ")
      request.keyword_is_or = toggleBtn.isChecked
      request.nickname = nickname_edit.text.trim().split(" ")
      request.setDateRange(
          toYYYYMMDD(datePickerStart.year, datePickerStart.month, datePickerStart.dayOfMonth),
          toYYYYMMDD(datePickerEnd.year, datePickerEnd.month, datePickerEnd.dayOfMonth))
      request.getQuery()

      val transaction = fragmentManager.beginTransaction()
      transaction.replace(R.id.fragment_frame, RecyclerFragment(request))
      transaction.addToBackStack(null)
      transaction.commit()
    }
  }

  fun toYYYYMMDD(year: Int, month: Int, day: Int): String {
    val month_ = "0" + month.toString()
    val day_ = "0" + day.toString()
    return year.toString() + month_.slice(month_.length-2..month_.length-1) + day_.slice(day_.length-2..day_.length-1)
  }
}