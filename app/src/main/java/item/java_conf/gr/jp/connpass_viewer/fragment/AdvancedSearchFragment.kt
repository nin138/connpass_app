package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest
import kotlinx.android.synthetic.main.advanced_search_fragment.*
import java.util.*


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
      request.date_range =  when(spinner.selectedItem.toString()) {
        "無制限" -> ConnpassRequest.SearchRange.UNLIMITED
        "から１週間" -> ConnpassRequest.SearchRange.ONE_WEEK
        "から２週間" -> ConnpassRequest.SearchRange.TWO_WEEK
        else -> ConnpassRequest.SearchRange.ONE_MONTH
      }
      datePickerStart.year
      request.start_date = Calendar.getInstance()
      request.start_date?.set(datePickerStart.year, datePickerStart.month, datePickerStart.dayOfMonth)

      val transaction = fragmentManager.beginTransaction()
      transaction.replace(R.id.fragment_frame, RecyclerFragment(request.getQuery()))
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