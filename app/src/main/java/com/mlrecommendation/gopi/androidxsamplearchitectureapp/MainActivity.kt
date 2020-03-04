package com.mlrecommendation.gopi.androidxsamplearchitectureapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.DB.sqlliteDB.SqlLiteDbTestActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic.MaskTestActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic.ViewAdvancedConceptsActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic.ViewConceptsActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.MvvmFirstActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle.TestLifecycleActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.RecyclerviewDiffUtilTestActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.diffUtil.DiffUtilTestActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.recyclerview.recyclerviewUpdatesChecker.RecyclerViewUpdatesCheckerActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxcode.RxCodeActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.HandlerAdvancedTestActivity

class MainActivity : AppCompatActivity() {

    val forwardActivity = SqlLiteDbTestActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, forwardActivity))
        finish()
    }


}
