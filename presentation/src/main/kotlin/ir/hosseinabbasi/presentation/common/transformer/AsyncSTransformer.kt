package ir.hosseinabbasi.presentation.common.transformer

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.schedulers.Schedulers
import ir.hosseinabbasi.domain.common.transformer.STransformer

/**
 * Created by Dr.jacky on 10/18/2018.
 */
class AsyncSTransformer<T> : STransformer<T>() {

    override fun apply(upstream: Single<T>): SingleSource<T> = upstream.subscribeOn(Schedulers.io())
}