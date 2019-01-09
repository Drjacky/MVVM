package ir.hosseinabbasi.presentation.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Dr.jacky on 9/19/2018.
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T : Any?> MutableLiveData<T>.defaultValue(initialValue: T) = apply { setValue(initialValue) }