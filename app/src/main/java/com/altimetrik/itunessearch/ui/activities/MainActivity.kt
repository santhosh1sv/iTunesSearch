package com.altimetrik.itunessearch.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.altimetrik.itunessearch.R
import com.altimetrik.itunessearch.models.Result
import com.altimetrik.itunessearch.ui.CustomClickListener
import com.altimetrik.itunessearch.ui.adapters.iTunesListAdapter
import com.altimetrik.itunessearch.utilities.*
import com.altimetrik.itunessearch.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CustomClickListener {
    private var viewModel: MainActivityViewModel? = null
    private var iTunesListAdapter: iTunesListAdapter? = null
    val list: ArrayList<Any> = ArrayList()
    val cartList: ArrayList<Result> = ArrayList()
    var selection: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        rvSearch.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            iTunesListAdapter = iTunesListAdapter(list, false, this@MainActivity)
            adapter = iTunesListAdapter
        }
        ivSearch?.setOnClickListener {
            if (isConnectingToInternet()) {
                if (TextUtils.isEmpty(etSearch.text.toString().trim())) {
                    showToast(getString(R.string.validate_search))
                } else {
                    prBar1.visibility = View.VISIBLE
                    reset()
                    loadItems(etSearch.text.toString().trim())
                }
            }
        }
        ivSort?.setOnClickListener {
            hideKeyboard()
            showSingleChoiceItemsDialog(getString(R.string.sort_dialog_title),R.array.sort, selection) {
                when {
                    it == 0 -> {
                        selection = 0
                        Collections.sort(list as List<Result>, Comparator<Result> { obj1, obj2 ->
                            val releaseDate1 = obj1.releaseDate ?: getString(R.string.no_content)
                            val releaseDate2 = obj2.releaseDate ?: getString(R.string.no_content)
                            releaseDate1.compareTo(
                                releaseDate2,
                                true
                            )
                        })
                    }
                    it == 1 -> {
                        selection = 1
                        Collections.sort(list as List<Result>, Comparator<Result> { obj1, obj2 ->
                            val artistName1 = obj1.artistName ?: getString(R.string.no_content)
                            val artistName2 = obj2.artistName ?: getString(R.string.no_content)
                            artistName2.compareTo(
                                artistName1,
                                true
                            )
                        }

                        )
                    }

                    it == 2 -> {
                        selection = 2
                        Collections.sort(list as List<Result>, Comparator<Result> { obj1, obj2 ->
                            val trackName1 = obj1.trackName ?: getString(R.string.no_content)
                            val trackName2 = obj2.trackName ?: getString(R.string.no_content)
                            trackName2.compareTo(
                                trackName1,
                                true
                            )
                        }

                        )
                    }

                    it == 3 -> {
                        selection = 3
                        Collections.sort(list as List<Result>, Comparator<Result> { obj1, obj2 ->
                            val collectionName1 =
                                obj1.collectionName ?: getString(R.string.no_content)
                            val collectionName2 =
                                obj2.collectionName ?: getString(R.string.no_content)
                            collectionName2.compareTo(
                                collectionName1,
                                true
                            )
                        }

                        )
                    }

                    it == 4 -> {
                        selection = 4
                        Collections.sort(list as List<Result>, Comparator<Result> { obj1, obj2 ->
                            val collectionPrice1 = obj1.collectionPrice ?: 0.0
                            val collectionPrice2 = obj2.collectionPrice ?: 0.0
                            collectionPrice2.compareTo(
                                collectionPrice1
                            )
                        }
                        )
                    }
                }
                iTunesListAdapter?.notifyDataSetChanged()
            }
        }
        ivCart?.setOnClickListener {
            if (cartList.size != 0) {
                val intent = Intent(this, SecondActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelableArrayList(AppConstants.INTENT_KEY_CART, cartList)
                intent.putExtras(bundle)
                startActivity(intent)
            } else
                showToast(getString(R.string.cart_empty))
        }
    }

    private fun reset() {
        iTunesListAdapter?.clear(list)
        selection = 0
        cartList.clear()
    }

    private fun loadItems(search: String) {
        viewModel?.getiTunes(search)?.observe(this, Observer {
            try {
                when {
                    it != null -> {
                        prBar1.visibility = View.GONE
                        tvNoData.visibility = View.GONE
                        hideKeyboard()
                        Collections.sort(it, Comparator<Result> { obj1, obj2 ->
                            val releaseDate1 = obj1.releaseDate ?: getString(R.string.no_content)
                            val releaseDate2 = obj2.releaseDate ?: getString(R.string.no_content)
                            releaseDate1.compareTo(
                                releaseDate2,
                                true
                            )
                        })
                        iTunesListAdapter?.addAll(list, it)
                        if (it.size > 1) {
                            ivSort.visibility = View.VISIBLE
                        } else
                            ivSort.visibility = View.GONE
                    }

                    else -> {
                        prBar1.visibility = View.GONE
                        tvNoData.visibility = View.VISIBLE
                        ivSort.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        })

    }

    override fun customClick(v: View, position: Int) {

        if ((v as CheckBox).isChecked) {
            cartList.add(list[position] as Result)
        } else {
            cartList.remove(list[position] as Result)
        }

    }


}








