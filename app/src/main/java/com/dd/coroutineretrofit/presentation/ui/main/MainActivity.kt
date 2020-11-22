package com.dd.coroutineretrofit.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dd.coroutineretrofit.R
import com.dd.coroutineretrofit.data.db.ProxyAddress
import com.dd.coroutineretrofit.presentation.ui.add.AddActivity
import com.dd.coroutineretrofit.presentation.ui.add.ProxyPresentation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UIEventManager {

    private lateinit var viewModel: MainActivityViewModel
    private var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = MainActivityViewModelFactory(this, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)


        viewModel.allProxies.observe(this, Observer {
            setDataToRecyclerView(it)

        })

        btnStart.setOnClickListener {
            if (started) {
                started = false
                btnStart.text = "Start"
                stopLoadData()
            } else {
                started = true
                btnStart.text = "Stop"
                loadData()
            }


        }

        btnAddProxy.setOnClickListener {
            val intent: Intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val list = data?.getParcelableArrayListExtra<ProxyPresentation>("any")
        if (list != null) {
            viewModel.insertAllProxy(list)
        }
    }

    private fun setDataToRecyclerView(list: List<ProxyAddress>) {
        recyclerViewMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            adapter = AdapterProxy(list)
        }
    }

    private fun loadData() {
        viewModel.isCancelIteration = false

        viewModel.startLoadDataFromWeb().observe(this, Observer {
            Log.i("autolog", "successfully sent $it")
            showToast("Successfully sent")
        })
    }

    private fun stopLoadData() {
        viewModel.isCancelIteration = true
    }


    override fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}
