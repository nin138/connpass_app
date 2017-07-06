package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.Setting
import kotlinx.android.synthetic.main.setting_fragment.*

class SettingFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return inflater.inflate(R.layout.setting_fragment, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Setting.userName
    userNameEdit.setText(if(Setting.userName != null) { Setting.userName } else { "" })
    if(Setting.isOr) radioOr.isChecked = true
    else radioAnd.isChecked =true
    when(Setting.searchRange) {
      0 -> spinner.setSelection(0)
      7 -> spinner.setSelection(1)
      14 -> spinner.setSelection(2)
      30 -> spinner.setSelection(3)
    }
    button.setOnClickListener {
      val searchRange = when (spinner.selectedItem.toString()) {
        "無制限" -> Setting.SearchRange.UNLIMITED
        "今日から１週間" -> Setting.SearchRange.ONE_WEEK
        "今日から２週間" -> Setting.SearchRange.TWO_WEEK
        else -> Setting.SearchRange.ONE_MONTH
      }
      Setting.set(context = activity, userName = userNameEdit.text.toString(), searchRange = searchRange, isOr = radioOr.isChecked)
    }
  }
}
