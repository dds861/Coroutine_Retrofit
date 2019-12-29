package com.dd.coroutineretrofit.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dd.coroutineretrofit.R
import com.dd.coroutineretrofit.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UIEventManager {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = MainActivityViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        init()
    }

    private fun init() {
        btn_clear.setOnClickListener {
            setDataToRecyclerView(listOf())
        }

        btn_load_data.setOnClickListener {
            loadData()
        }
    }

    private fun setDataToRecyclerView(userList: List<User>) {
        recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = AdapterUser(userList)
        }
    }

    private fun loadData() {
        viewModel.loadDataFromWeb().observe(this, Observer {
            setDataToRecyclerView(it)
        })
    }

    override fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}
