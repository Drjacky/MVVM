package ir.hosseinabbasi.mvvm.common.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Created by Dr.jacky on 9/12/2018.
 */
fun AppCompatActivity.replaceFragment(savedInstanceState: Bundle?, @IdRes where: Int,
                                      fragment: Fragment, tag: String) {
    if (savedInstanceState == null)
        supportFragmentManager.beginTransaction()
                .replace(where, fragment, tag)
                .commit()
}