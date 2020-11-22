package com.dd.coroutineretrofit.presentation.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.dd.coroutineretrofit.R
import com.dd.coroutineretrofit.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        btnSave.setOnClickListener {


//            Log.i("autolog", "etProxies.text: ${etProxies.text.toString()}")
//            Log.i("autolog", "etProxies.text.toString().split(\"\\\\n\"): ${etProxies.text.toString().split("\n")}")

            val proxies = etProxies.text.toString().split("\n")

//                .map {
//                    if (Patterns.IP_ADDRESS.matcher(it).matches()) it else "1.1.1.1:80"
//                }

            val proxies2: ArrayList<ProxyPresentation> = proxies.map {
                ProxyPresentation(
                    ip = it.substring(0, it.indexOf(":")),
                    port = it.substring(it.indexOf(":") + 1),
                    isUsed = false,
                    isSuccessfullySent = false,
                    requestFailureDescription = "unknown",
                    voteCounter = 0
                )
            }.toCollection(ArrayList())


//            if(P)

            val intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra(
                "any",
                proxies2
            )
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}