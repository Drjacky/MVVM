package ir.hosseinabbasi.mvvm.ui.base

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher


/**
 * Created by Dr.jacky on 9/20/2018.
 */
open class BaseViewModel : ViewModel() {

    private val mDisposables = CompositeDisposable()

    protected fun Disposable.track() {
        mDisposables.add(this)
    }

    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
    }

    protected fun <T> MediatorLiveData<T>.add(publisher: Publisher<T>) {
        addSource(LiveDataReactiveStreams.fromPublisher(publisher)) {
            postValue(it)
        }
    }
}