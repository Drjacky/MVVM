package ir.hosseinabbasi.mvvm.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.mvvm.R
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
import ir.hosseinabbasi.mvvm.common.extension.observe
import ir.hosseinabbasi.mvvm.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Created by Dr.jacky on 9/7/2018.
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener/*, Paginate.Callbacks*/ {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var isLoading = false
    //private var hasLoadedAllItems = false

    private val adapter: AlbumListAdapter by lazy {
        AlbumListAdapter()
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

/*    private val paginate: Paginate by lazy {
        Paginate.with(fragmentHomeRcyMain, this).setLoadingTriggerThreshold(3)
                .addLoadingListItem(true).build()
    }*/

    private fun onAlbumDeleted(resultState: ResultState<Int>) {
        when (resultState) {
            is ResultState.Success -> Toast.makeText(activity, "album ${resultState.data} deleted", Toast.LENGTH_SHORT).show()
            is ResultState.Error -> Toast.makeText(activity, resultState.throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAlbums(albums: ResultState<PagedList<Entity.Album>>) {
        /*if (viewModel.page == 0) fragmentHomeRcyMain.scrollToPosition(0)
        fragmentHomeSwp.isRefreshing = false
        isLoading = false*/
        //adapter.submitList(albums)
        when (albums) {
            is ResultState.Success -> {
                hideLoading()
                adapter.submitList(albums.data)
            }
            is ResultState.Error -> {
                hideLoading()
                Toast.makeText(activity, albums.throwable.message, Toast.LENGTH_SHORT).show()
                adapter.submitList(albums.data)
            }
            is ResultState.Loading -> {
                adapter.submitList(albums.data)
            }
        }
        //if (viewModel.pageNumberLiveData.value == 0) fragmentHomeRcyMain.scrollToPosition(0)
        isLoading = false
        fragmentHomeSwp.isRefreshing = false
        //if (hasLoadedAllItems) paginate.setHasMoreDataToLoad(false)
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        fragmentHomeSwp.isRefreshing = true
        fragmentHomeSwp.setOnRefreshListener(this)
        fragmentHomeRcyMain.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        //fragmentHomeRcyMain.layoutManager = LinearLayoutManager(activity)
        fragmentHomeRcyMain.setHasFixedSize(true)
        fragmentHomeRcyMain.adapter = adapter

        adapter.albumItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.deleteAlbum(it)
        }

        //paginate

        showLoading()
    }

/*    private fun observeNetworkList() {
        observe(viewModel.networkList) {
            fragmentHomeSwp.isRefreshing = false
            isLoading = false
            when (it) {
                is ResultState.Success -> {
                    if (it.data.lastOrNull()?.id == 100L) {
                        hasLoadedAllItems = true
                        if (hasLoadedAllItems) paginate.setHasMoreDataToLoad(false)
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(activity, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? = inflater.inflate(
            R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe(viewModel.albumsLiveData, ::showAlbums)
        observe(viewModel.deletedAlbumLiveData, ::onAlbumDeleted)
        viewModel.getAlbums()
        //observeNetworkList()
    }

    override fun onRefresh() {
        /*fragmentHomeSwp.isRefreshing = true
        hasLoadedAllItems = false
        if (hasLoadedAllItems) paginate.setHasMoreDataToLoad(true)
        isLoading = true
        viewModel.refresh()*/
        //observe(viewModel.albumsLiveData(true), ::showAlbums)
        //observeNetworkList()
    }

    /*override fun onLoadMore() {
        if (isLoading) return

        isLoading = true
        viewModel.getAlbums()
    }

    override fun isLoading(): Boolean = isLoading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems*/
}