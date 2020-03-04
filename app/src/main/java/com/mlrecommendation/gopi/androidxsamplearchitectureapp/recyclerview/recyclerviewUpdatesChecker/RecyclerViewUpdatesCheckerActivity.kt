package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.recyclerviewUpdatesChecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast
import kotlinx.android.synthetic.main.activity_recycler_view_updates_checker.*

class RecyclerViewUpdatesCheckerActivity : AppCompatActivity() {

    lateinit var list: MutableList<GopiRecycleItem>
    lateinit var adapter: RecyclerViewUpdatesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_updates_checker)

        list = mutableListOf<GopiRecycleItem>();
        for(i in 1..10) {
            list.add(GopiRecycleItem(i.toString(), i*20))
        }
        adapter = RecyclerViewUpdatesAdapter(list){item, position ->  }
        recycleTv.apply {  layoutManager = LinearLayoutManager(this@RecyclerViewUpdatesCheckerActivity); adapter = this@RecyclerViewUpdatesCheckerActivity.adapter }
        adapter.notifyDataSetChanged()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { showToast("after 5 sec");list.add(GopiRecycleItem("DUMMY adding", -2)); adapter.notifyItemInserted(list.size) },5000)
    }
}
