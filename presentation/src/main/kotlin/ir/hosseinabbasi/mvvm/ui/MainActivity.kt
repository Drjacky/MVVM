package ir.hosseinabbasi.mvvm.ui

import android.os.Bundle
import ir.hosseinabbasi.mvvm.R
import ir.hosseinabbasi.mvvm.ui.base.BaseActivity

/**
 * Created by Dr.jacky on 9/7/2018.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getNavControllerId(): Int = R.id.activityMainHomeHostFragment
}