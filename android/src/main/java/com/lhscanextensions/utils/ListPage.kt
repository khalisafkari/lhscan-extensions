package com.lhscanextensions.utils

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException

class ListPage {
  private val client = OkHttpClient()

  @Throws(IOException::class)
  private fun run(url:String):String? {
    val request = Request.Builder()
      .url(url)
      .addHeader("referer","https://loveheaven.net")
      .addHeader("x-app","com.dev.lhscan")
      .build()
    client.newCall(request).execute().use({ response -> return response.body()?.string() })
  }

  @Throws(IOException::class)
  fun get(url: String): ReadableArray? {
    val array = WritableNativeArray()
    val result = run(url)
    val document = Jsoup.parse(result)
    val elements = document.select(".row.top div .media")
    for (element in elements) {
      array.pushMap(parseHTMLList(element))
    }
    return array
  }

  @Throws(IOException::class)
  fun getByList(): ReadableArray? {
    val array = WritableNativeArray()
    val result = run("https://loveheaven.net/manga-list.html?listType=allABC")
    val document = Jsoup.parse(result)
    val elements = document.select("div#Character div span b a")
    for (elemenet in elements) {
      array.pushMap(parseByHTMLList(elemenet))
    }
    return array
  }

  @Throws(IOException::class)
  fun getByGenre(): ReadableArray? {
    val array = WritableNativeArray()
    val result = run("https://loveheaven.net/manga-list-genre-action.html")
    val document = Jsoup.parse(result)
    val elements = document.select(".panel-body div a")
    for (element in elements) {
      array.pushMap(parseByGenreList(element))
    }
    return array
  }

  private fun parseByGenreList(element: Element): ReadableMap? {
    val map = WritableNativeMap()
    map.putString("id",element.attr("href").trim())
    map.putString("title",element.text().trim())
    map.putString("des",element.attr("title").trim())
    return map
  }

  private fun parseByHTMLList(element: Element): ReadableMap? {
    val map = WritableNativeMap();
    map.putString("id",element.attr("href").trim())
    map.putString("title",element.text().trim())
    return map
  }

  private fun parseHTMLList(element: Element): ReadableMap? {
    val map = WritableNativeMap();
    map.putString("id",element.select("a").attr("href"))
    map.putString("image",element.select("a img").attr("data-src"))
    map.putString("title",element.select(".media-body h3 a").text().trim())
    map.putInt("view",element.select(".media-body span").text().replace("\\D+".toRegex(),"").toInt())
    map.putString("chapter",element.select(".media-body a").text().trim())
    return map;
  }
}
