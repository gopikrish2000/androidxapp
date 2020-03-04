package com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.diffUtil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.copyOf
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import kotlinx.android.synthetic.main.activity_diff_util.*

class DiffUtilTestActivity : AppCompatActivity() {

    lateinit var adapter: DiffUtilAdapter
    var items = mutableListOf<DiffUtilItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)

        diffRv.layoutManager = LinearLayoutManager(this)
        adapter = DiffUtilAdapter() { item, position -> }
        diffRv.adapter = adapter
        updateData(1,10, true)
        diffIntroTv.setOnClickListener { updateData(ThreadUtils.randomNumberInRange(5,5),10,true) }
    }

    private fun updateData(start: Int, end: Int, isStarting: Boolean) {
        items.clear()
        for (i in start..end){
            items.add(DiffUtilItem(if(isStarting) i.toString() else ThreadUtils.randomNumberInRange(50,500).toString(), ThreadUtils.randomNumberInRange(60,99), i.toString()))
        }
//        adapter.updateData(ArrayList(items))
        adapter.updateData((items))
    }
}
