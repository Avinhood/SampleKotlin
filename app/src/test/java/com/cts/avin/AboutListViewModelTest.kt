package com.cts.avin

import android.content.Context
import android.inputmethodservice.Keyboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.cts.avin.data.main.ListData
import com.cts.avin.data.main.Rows
import com.cts.avin.network.ApiService
import com.cts.avin.ui.main.AboutListFragment
import com.cts.avin.util.ViewModelFactory
import com.cts.avin.viewmodel.AboutListViewModel

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Single

import org.junit.Assert.assertEquals
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner::class)
class AboutListViewModelTest {

    @Rule
    var rule = InstantTaskExecutorRule()
    @Mock
    private val listData: MutableLiveData<ListData>? = null
    @Mock
    internal var aboutListFragment: AboutListFragment? = null
    @Mock
    internal var apiInterface: ApiService.ApiInterface? = null

    internal var mock: AboutListViewModel
    internal var aboutListViewModel: AboutListViewModel

    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
        val apiService = ApiService(apiInterface)
        aboutListViewModel = AboutListViewModel(apiService)

        mock = org.mockito.Mockito.mock(AboutListViewModel::class.java)
        Mockito.`when`(mock.mainListData).thenReturn(generateAboutListResponse())
    }

    @Test
    fun test_aboutListData() {
        Assert.assertEquals(mock.mainListData.value!!.rows.size.toLong(), 10)

    }

    private fun generateAboutListResponse(): MutableLiveData<ListData> {
        val ldata = MutableLiveData<ListData>()
        ldata.value = ListData()
        ldata.value!!.title = "About app"
        val itemArray = ArrayList<Rows>()
        for (i in 0..9) {
            val item = Rows()
            item.title = "Title$i"
            item.description = "Desc$i"
            item.imageHref = "Image$i"
            itemArray.add(item)
        }
        ldata.value!!.rows = itemArray
        return ldata
    }

    @Test
    fun whenAboutListIsNotNull() {
        Assert.assertNotNull(mock.mainListData)

    }

}
