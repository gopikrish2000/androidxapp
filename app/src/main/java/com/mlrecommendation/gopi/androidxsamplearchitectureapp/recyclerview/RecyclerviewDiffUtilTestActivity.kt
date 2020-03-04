package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.layout
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.dpToPx
import kotlinx.android.synthetic.main.activity_recyclerview_diff_util_test.*


class RecyclerviewDiffUtilTestActivity : AppCompatActivity() {
    lateinit var items: MutableList<GopiItem>
    lateinit var adapter: GopiRecyclerviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_recyclerview_diff_util_test)

        val oneItem = GopiItem("www", 666)
        items = mutableListOf<GopiItem>(GopiItem("zero",20), GopiItem("1",33), GopiItem("2",44), GopiItem("3"), GopiItem("4",888), GopiItem("5"), GopiItem("6"), GopiItem("7"), GopiItem("8"), GopiItem("9"), GopiItem("10"))
        testRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        adapter = GopiRecyclerviewAdapter(items){ item, position ->
//            item.name = item.name + ThreadUtils.randomNumberInRange(0,9)
//            adapter.notifyItemChanged(position, "gopipayload") // just with this flickering stopped even if u dont handle payload
//            adapter.notifyItemChanged(position)  // flickering is possible in this case
//            val itemRemoved = items.removeAt(position)
//            items.add(0, itemRemoved)
            /*Collections.swap(items, position, 0)
            adapter.notifyItemMoved(position,0)
            adapter.notifyItemMoved(0,position)*/
//            adapter.notifyItemRemoved(position)
//            adapter.notifyItemInserted(0)
//            adapter.notifyItemChanged(0)

            //testRv.post {  testRv.scrollTo(0,0) }
        }
        testRv.adapter = adapter

        testRv.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

                super.getItemOffsets(outRect, view, parent, state)
                val px = dpToPx(16)
                outRect.set(px,px,px,px) // set offsets to the view works like padding.
//                view.setBackgroundColor(ContextCompat.getColor(MyApplication.getInstance(), color.colorAccent)) // can change backgrounds...
            }
        })
//        testRv.itemAnimator = NoAnimationItemAnimator()
//        (testRv.getItemAnimator() as SimpleItemAnimator).setSupportsChangeAnimations(false)

        testRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                println("RecyclerviewDiffUtilTestActivity.onScrolled with dx $dx dy $dy")
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        testRv.onFlingListener = object : RecyclerView.OnFlingListener(){
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
//                println("RecyclerviewDiffUtilTestActivity.onFling called with velX $velocityX velY $velocityY")
                return true
            }
        }

        recycleTv.setOnClickListener {
            items[0].name += "PP"
            adapter.notifyDataSetChanged() }

//        testRv.setI
        println("requestedOrientation $requestedOrientation")
    }

    private class NoAnimationItemAnimator : SimpleItemAnimator() {
        override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
            dispatchRemoveFinished(holder)
            return false
        }

        override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
            dispatchAddFinished(holder)
            return false
        }

        override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
            dispatchMoveFinished(holder)
            return false
        }

        override fun animateChange(oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder, fromX: Int,
            fromY: Int, toX: Int,toY: Int ): Boolean {
            dispatchChangeFinished(oldHolder, true)
            dispatchChangeFinished(newHolder, false)

            return false
        }

        override fun runPendingAnimations() {
            // stub
        }

        override fun endAnimation(item: RecyclerView.ViewHolder) {
            // stub
        }

        override fun endAnimations() {
            // stub
        }

        override fun isRunning(): Boolean {
            return false
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) { // when u handle configuration change in Manifest then recyclerview nothing is called.
                                                                    // So u have to notify urself if u want portrait mode different width & landscape mode different width
        super.onConfigurationChanged(newConfig)
        println("RecyclerviewDiffUtilTestActivity.onConfigurationChanged with orientation ${if(newConfig.orientation == 1) "portrait" else "landscape"} ")
        adapter.notifyDataSetChanged()
    }
}
