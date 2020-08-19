package com.altimetrik.itunessearch.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.altimetrik.itunessearch.R
import com.altimetrik.itunessearch.models.Result
import com.altimetrik.itunessearch.ui.adapters.iTunesListAdapter
import com.altimetrik.itunessearch.utilities.AppConstants
import com.altimetrik.itunessearch.utilities.showActionBar
import kotlinx.android.synthetic.main.activity_cart.*
import java.util.*
import kotlin.Comparator


class SecondActivity : AppCompatActivity() {
    private var iTunesListAdapter: iTunesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        showActionBar(toolBar,getString(R.string.app_cart_title))
        val bundle = intent?.extras
        val list = bundle?.getParcelableArrayList<Result>(AppConstants.INTENT_KEY_CART)
        Collections.sort(list as MutableList<Result>, Comparator<Result> { obj1, obj2 ->
            val releaseDate1 = obj1.releaseDate ?: getString(R.string.no_content)
            val releaseDate2 = obj2.releaseDate ?: getString(R.string.no_content)
            releaseDate1.compareTo(
                releaseDate2,
                true
            )
        })
        rvCart.apply {
            layoutManager = LinearLayoutManager(this@SecondActivity)
            iTunesListAdapter = iTunesListAdapter(list as ArrayList<Any>, true, null)
            adapter = iTunesListAdapter
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}