package ir.hosseinabbasi.data.repository

import androidx.paging.PagedList
import io.reactivex.*
import io.reactivex.processors.PublishProcessor
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.repository.BaseRepository
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by Dr.jacky on 10/11/2018.
 */
/**
 * The base repository to handle important functionality of repositories.
 *
 * @param E the entity that is related to this repository. Every repository responsible for
 * one and only one entity.
 */
abstract class BaseRepositoryImpl<E : Entity> : BaseRepository {

    /**
     * Loads an entity from database and network.
     *
     * @param D a data type
     * @param databaseFlowable a flowable to load an entity from database.
     * @param netSingle a single to load an entity from server.
     * @param persist a high order function to replace an entity.
     */
    protected fun <D> perform(
            databaseFlowable: Flowable<List<D>>,
            netSingle: Single<D>,
            persist: (D) -> Unit
    ): Flowable<ResultState<D>> {

        val data = AtomicReference<D>()
        val processor = PublishProcessor.create<ResultState<D>>()
        val f = databaseFlowable.doOnNext { data.set(it.firstOrNull()) }.share()

        return Flowable.merge(f.take(1)
                .flatMap {
                    if (it.isEmpty()) {
                        handleNetSingle(netSingle, persist, data.get())
                    } else {
                        concatJustFlowable(
                                ResultState.Loading(it.first()),
                                handleNetSingle(netSingle, persist, data.get())
                        )
                    }
                }, f.skip(1)
                .flatMap {
                    if (it.isNotEmpty()) {
                        concatJustFlowable(ResultState.Success(it.first()), processor)
                    } else {
                        processor
                    }
                })
                .onErrorResumeNext(io.reactivex.functions.Function {
                    concatJustFlowable(ResultState.Error(it, data.get()), processor)
                })
    }

    /**
     * Loads a list of entities from database and network.
     *
     * @param D a data type
     * @param databaseFlowable a flowable to load entities from database.
     * @param netSingle a single to load entities from server.
     * @param persist a high order function to replace a list of entities.
     */
    protected fun <D> performList(
            databaseFlowable: Flowable<PagedList<D>>,
            netSingle: Single<List<D>>,
            persist: (List<D>) -> Unit
    ): Flowable<ResultState<PagedList<D>>> {

        val data = AtomicReference<PagedList<D>>()
        val processor = PublishProcessor.create<ResultState<PagedList<D>>>()
        val f = databaseFlowable.doOnNext { data.set(it) }.share()

        return Flowable.merge(f.take(1)
                .flatMap {
                    concatJustFlowable(
                            ResultState.Loading(it),
                            handleNetSingleList(netSingle, persist, data.get()))
                }, f.skip(1)
                .flatMap {
                    concatJustFlowable(ResultState.Success(it), processor)
                })
                .onErrorResumeNext(io.reactivex.functions.Function {
                    concatJustFlowable(ResultState.Error(it, data.get()), processor)
                })
    }

    private fun <D> concatJustFlowable(
            d: ResultState<D>,
            flowable: Flowable<ResultState<D>>
    ) = Flowable.concat(Flowable.just<ResultState<D>>(d), flowable)

    private fun <D> handleNetSingle(
            netSingle: Single<D>,
            persist: (D) -> Unit,
            cachedData: D
    ): Flowable<ResultState<D>> = netSingle.toFlowable().flatMap {
        persist(it)
        Completable.complete().toFlowable<ResultState<D>>()
    }.onErrorReturn {
        ResultState.Error(it, cachedData)
    }

    private fun <D> handleNetSingleList(
            netSingle: Single<List<D>>,
            persist: (List<D>) -> Unit,
            cachedData: PagedList<D>
    ): Flowable<ResultState<PagedList<D>>> = netSingle.toFlowable().flatMap {
        persist(it)
        Completable.complete().toFlowable<ResultState<PagedList<D>>>()
    }.onErrorReturn {
        ResultState.Error(it, cachedData)
    }

    /**
     * Wrap [ResultState] into [SingleTransformer]
     */
    protected fun <D> wrapResultSingle(): SingleTransformer<D, ResultState<D>> {
        return SingleTransformer {
            it.map { d -> ResultState.Success(d) as ResultState<D> }
                    .onErrorReturn { e -> ResultState.Error(e, null) }
        }
    }

    /**
     * Wrap [ResultState] into [FlowableTransformer]
     */
    protected fun <D> wrapResultFlowable(): FlowableTransformer<D, ResultState<D>> {
        return FlowableTransformer {
            it.map { d -> ResultState.Success(d) as ResultState<D> }
                    .onErrorReturn { e -> ResultState.Error(e, null) }
        }
    }
}