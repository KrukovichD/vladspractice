package com.example.first_app.Data.Repository

import android.content.Context
import android.util.Log
import com.example.first_app.util.Helper
import com.example.first_app.Data.models.Root

class RepositoryParcer {

    fun parserXML(context: Context, fileName: String): Root {
        var helper = Helper()
        var start = System.nanoTime()
        var root = helper.parserXML(helper.readFile(context, fileName)!!)
        var end = System.nanoTime()
        Log.d("Size in start", "${(end-start) /1_000_000.0}")
        Log.d("Size in start", "${root.NS_SEMK.size}")
        return root
    }
}