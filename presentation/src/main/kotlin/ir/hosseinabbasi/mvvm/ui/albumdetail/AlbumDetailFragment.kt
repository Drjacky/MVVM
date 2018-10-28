package ir.hosseinabbasi.mvvm.ui.albumdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.hosseinabbasi.mvvm.R
import ir.hosseinabbasi.mvvm.ui.base.BaseFragment

/**
 * Created by Dr.jacky on 9/7/2018.
 */
class AlbumDetailFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_album_detail, container, false)
}