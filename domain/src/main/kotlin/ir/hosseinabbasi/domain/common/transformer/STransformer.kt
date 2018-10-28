package ir.hosseinabbasi.domain.common.transformer

import io.reactivex.SingleTransformer

/**
 * Created by Dr.jacky on 10/15/2018.
 */
/**
 * A transformer to io thread for singles.
 */
abstract class STransformer<T> : SingleTransformer<T, T>