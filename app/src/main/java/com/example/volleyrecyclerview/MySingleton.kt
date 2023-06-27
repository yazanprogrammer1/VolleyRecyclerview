package com.example.volleyrecyclerview

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton : Application() {

    val TAG = "YAZAN"
    private var myRequsetQueue: RequestQueue? = null

    companion object {
        private var mInstance: MySingleton? = null
        fun getInstance(): MySingleton {
            return mInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    private fun getRequestQueue(): RequestQueue? {
        if (myRequsetQueue == null) {
            myRequsetQueue = Volley.newRequestQueue(this)
        }
        return myRequsetQueue
    }

    fun <T> addRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()!!.add(req)
    }

    fun <T> addRequestQueue(req: Request<T>, tag: String) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue()!!.add(req)
    }

    fun cancelPendingRequest(tag: Any?) {
        if (myRequsetQueue != null) {
            myRequsetQueue!!.cancelAll(tag)
        }
    }
}