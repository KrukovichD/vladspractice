package com.example.first_app.util

import android.content.Context
import android.util.Log
import com.example.first_app.Data.models.NS_SEMK
import com.example.first_app.Data.models.Root
import com.google.gson.GsonBuilder
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Stack


class Helper {

    fun readFile(context: Context,fileName: String ): String? {
        try {
            context.assets.open(fileName).use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    BufferedReader(reader).use { bufferedReader ->
                        val stringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line)
                        }
                        return stringBuilder.toString()
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("JsonManager", "Error reading JSON from assets", e)
            return null
        }
    }

    fun parserXML(xmlString: String): Root {
        val startTime = System.nanoTime()

        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(xmlString.reader())

        val tags = Stack<String>()

        var KMC_text = ""
        var KRK_text = ""
        var KT_text = ""
        var EMK_text = ""
        var PR_text = ""
        var KTARA_text = ""
        var GTIN_text: String? = null
        var EMKPOD_text = ""
        val NS_SEMK_list = mutableListOf<NS_SEMK>()
        var root: Root? = null

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    tags.push(parser.name)
                }
                XmlPullParser.TEXT -> {
                    when (tags.peek()) {
                        "KMC" -> KMC_text += parser.text
                        "KRK" -> KRK_text += parser.text
                        "KT" -> KT_text += parser.text
                        "EMK" -> EMK_text += parser.text
                        "PR" -> PR_text += parser.text
                        "KTARA" -> KTARA_text += parser.text
                        "GTIN" -> GTIN_text = parser.text
                        "EMKPOD" -> EMKPOD_text += parser.text
                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "NS_SEMK" -> {
                            NS_SEMK_list.add(
                                NS_SEMK(
                                    KMC = KMC_text,
                                    KRK = KRK_text,
                                    KT = KT_text,
                                    EMK = EMK_text,
                                    PR = PR_text,
                                    KTARA = KTARA_text,
                                    GTIN = GTIN_text,
                                    EMKPOD = EMKPOD_text
                                )
                            )
                            KMC_text = ""
                            KRK_text = ""
                            KT_text = ""
                            EMK_text = ""
                            PR_text = ""
                            KTARA_text = ""
                            GTIN_text = null
                            EMKPOD_text = ""
                        }
                        "Root" -> {
                            root = Root(NS_SEMK_list)
                        }
                    }
                    tags.pop()
                }
            }
            eventType = parser.next()
        }

        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1_000_000.0
        Log.d("XMLparser", " time parsing $duration")
        return root ?: throw Exception("Parsing failed, root is null")
    }


    fun parseJSON(jsonString: String): Root? {
        try {
            val startTime = System.nanoTime()

            val gson = GsonBuilder().create()
            val root = gson.fromJson(jsonString, Root::class.java)

            val endTime = System.nanoTime()
            val duration = (endTime - startTime) / 1_000_000.0
            Log.d("XMLparser", "$duration")
            return root
        } catch (e: Exception) {
            Log.e("JsonManager", "Error parsing JSON", e)
            return null
        }
    }

}
