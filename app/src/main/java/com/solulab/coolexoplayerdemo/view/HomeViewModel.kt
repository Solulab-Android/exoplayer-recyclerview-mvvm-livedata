package com.solulab.coolexoplayerdemo.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solulab.coolexoplayerdemo.network.BaseModel
import com.solulab.coolexoplayerdemo.network.CallbackObserver
import com.solulab.coolexoplayerdemo.network.Networking
import com.tofik.coolexoplayer.exoplayer.cool.widget.PressablePlayerSelector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {
    private lateinit var mcontext: Context

    private var selector: PressablePlayerSelector? = null
    private val videoList: ArrayList<HomeData> = ArrayList()
    private var homeLiveData: MutableLiveData<List<HomeData>> = MutableLiveData()

    private lateinit var homeAdapter: HomeAdapter


    fun init(context: Context) {

        mcontext = context
        homeAdapter = HomeAdapter(videoList, selector, mcontext)
        homeLiveData.observeForever {
            if (it != null) {
                videoList.clear()
                videoList.addAll(it)
                homeAdapter.notifyDataSetChanged()
            }
        }
        getHomeList()
    }

    fun setSelector(selector: PressablePlayerSelector) {
        this.selector = selector
    }


    fun getHomeAdapter(): HomeAdapter = homeAdapter


    fun getHomeList() {
        Networking
            .with()
            .getServices()
            .getHomeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CallbackObserver<BaseModel<List<HomeData>>>() {
                override fun onSuccess(response: BaseModel<List<HomeData>>) {
                    homeLiveData.postValue(response.data)
                }

                override fun onFailed(code: Int, message: String) {
                    homeLiveData.postValue(null)
                }
            })
    }
}