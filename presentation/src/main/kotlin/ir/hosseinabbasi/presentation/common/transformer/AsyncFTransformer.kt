package ir.hosseinabbasi.presentation.common.transformer

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import ir.hosseinabbasi.domain.common.transformer.FTransformer
import org.reactivestreams.Publisher

/**
 * Created by Dr.jacky on 10/14/2018.
 */
class AsyncFTransformer<T> : FTransformer<T>() {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
            upstream.subscribeOn(Schedulers.io())
}