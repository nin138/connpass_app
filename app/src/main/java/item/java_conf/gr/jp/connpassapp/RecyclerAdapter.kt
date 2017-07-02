package item.java_conf.gr.jp.connpassapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import item.java_conf.gr.jp.connpassapp.entity.Event


import java.text.SimpleDateFormat
import java.util.*

class RecyclerAdapter(private val context: Context, private var list: Array<Event>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), View.OnClickListener {
  interface OnItemClickListener {
    fun onItemClick(adapter: RecyclerAdapter, position: Int, event: Event)
  }

  private var recycler: RecyclerView? = null
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private var clickListener: OnItemClickListener? = null
  private val dateFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
  private val calender = Calendar.getInstance()
  private val days = arrayOf("日", "月", "火", "水", "木", "金", "土")


  fun setList(list: Array<Event>) {
    this.list = list
    recycler?.adapter = this
  }

  fun setOnItemClickListener(listener: OnItemClickListener) {
    clickListener = listener
  }

  override fun onClick(view: View) {
    if (recycler != null && clickListener != null) {
      val position = recycler!!.getChildAdapterPosition(view)
      clickListener?.onItemClick(this, position, list[position])
    }
  }

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
    super.onAttachedToRecyclerView(recyclerView)
    recycler = recyclerView
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
    super.onDetachedFromRecyclerView(recyclerView)
    recycler = null
  }


  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
    val view = inflater.inflate(R.layout.list_item, viewGroup, false)
    view.setOnClickListener(this)
    return ViewHolder(view)
  }


  override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
    if (list.size > i) {
      viewHolder.textView.text = list[i].title
      val st = list[i].started_at
      calender.time = dateFmt.parse(st)
      viewHolder.year.text = st.substring(0, 4)
      viewHolder.date.text = context.getString(R.string.date, st.substring(5, 7), st.substring(8, 10))
      viewHolder.day.text = context.getString(R.string.day, days[calender.get(Calendar.DAY_OF_WEEK)-1])
      viewHolder.time.text = context.getString(R.string.time, st.substring(11, 16))
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView
    init {
      textView = itemView.findViewById<TextView>(R.id.list_item_text)
    }
    val year = itemView.findViewById<TextView>(R.id.year)
    val date = itemView.findViewById<TextView>(R.id.date)
    val day = itemView.findViewById<TextView>(R.id.day)
    val time = itemView.findViewById<TextView>(R.id.time)
  }

}
