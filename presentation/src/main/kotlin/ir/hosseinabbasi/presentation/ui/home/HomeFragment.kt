package ir.hosseinabbasi.presentation.ui.home

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
import ir.hosseinabbasi.presentation.R
import ir.hosseinabbasi.presentation.common.extension.applyIoScheduler
import ir.hosseinabbasi.presentation.common.extension.observe
import ir.hosseinabbasi.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Created by Dr.jacky on 9/7/2018.
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var isLoading = false

    private val adapter: AlbumListAdapter by lazy {
        AlbumListAdapter()
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun onAlbumDeleted(resultState: ResultState<Int>) {
        when (resultState) {
            is ResultState.Success -> Toast.makeText(activity, "album ${resultState.data} deleted", Toast.LENGTH_SHORT).show()
            is ResultState.Error -> Toast.makeText(activity, resultState.throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAlbums(albums: ResultState<PagedList<Entity.Album>>) {
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
        isLoading = false
        fragmentHomeSwp.isRefreshing = false
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        fragmentHomeSwp.isRefreshing = true
        fragmentHomeSwp.setOnRefreshListener(this)
        fragmentHomeRcyMain.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        fragmentHomeRcyMain.setHasFixedSize(true)
        fragmentHomeRcyMain.adapter = adapter

        adapter.albumItemClickEvent.applyIoScheduler().subscribe { it ->
            viewModel.deleteAlbum(it)
        }

        showLoading()
    }

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
    }

    override fun onRefresh() {
    }
}