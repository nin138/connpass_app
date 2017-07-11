package item.java_conf.gr.jp.connpass_viewer.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import item.java_conf.gr.jp.connpass_viewer.EventDetailActivity
import item.java_conf.gr.jp.connpass_viewer.R
import item.java_conf.gr.jp.connpass_viewer.RecyclerAdapter
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassRequest
import item.java_conf.gr.jp.connpass_viewer.entity.Event
import item.java_conf.gr.jp.connpass_viewer.entity.SerializableEvent
import kotlinx.android.synthetic.main.recycler_fragment.*
import android.support.v7.widget.helper.ItemTouchHelper




class RecyclerFragment() : Fragment() {
  var adaptor: RecyclerAdapter? = null
  var isLoading = false
  var request: ConnpassRequest? = null
  constructor(request: ConnpassRequest): this() {
    this.request = request
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if(adaptor == null) adaptor = RecyclerAdapter(activity, ArrayList(), this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    return inflater.inflate(R.layout.recycler_fragment, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if(request == null || request != savedInstanceState?.getSerializable("request") as ConnpassRequest?) {
      val layoutManager = LinearLayoutManager(activity)
      recycler_view.layoutManager = layoutManager
      recycler_view.adapter = adaptor
      if(request != null)updateList()
    }
    adaptor!!.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
      override fun onItemClick(adapter: RecyclerAdapter, position: Int, event: Event) {
        val intent = Intent(activity, EventDetailActivity::class.java)
        intent.putExtra("event", SerializableEvent(event))
        startActivity(intent)
      }
    })
    recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy == 0 || isLoading) return
        val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition() + 1
        if(totalItemCount < lastVisibleItem + 5) {
          updateList()
        }
      }
      override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
      }
    })

//    ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
//        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)

    val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
      override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
      }
      override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
      }
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adaptor!!.onItemSwiped(viewHolder.adapterPosition, activity)
      }
    })
    helper.attachToRecyclerView(recycler_view)
    recycler_view.addItemDecoration(helper)
  }
  fun updateList() {
    if(request!!.finished) return
    isLoading = true
    activity.findViewById<View>(R.id.gifView).visibility = View.VISIBLE
    request!!.getList(object : ConnpassRequest.Result {
      override fun onSuccess(list: List<Event>) {
        adaptor!!.addList(list)
        isLoading = false
        activity.findViewById<View>(R.id.gifView).visibility = View.INVISIBLE
        if(adaptor!!.itemCount < 6) updateList()
      }
      override fun onError() {
        Toast.makeText(activity, "net connection error", Toast.LENGTH_SHORT).show()
        isLoading = false
        activity.findViewById<View>(R.id.gifView).visibility = View.INVISIBLE
      }
    })
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putSerializable("request", request)
  }
}