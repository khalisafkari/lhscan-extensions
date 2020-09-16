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

class PostsPage {
  private val client = OkHttpClient()

  @Throws(IOException::class)
  private fun run(url:String): String? {
    val request = Request.Builder()
      .url(url)
      .addHeader("referer","https://loveheaven.net")
      .addHeader("x-app","com.dev.lhscan")
      .build()
    client.newCall(request).execute().use({ response -> return response.body()?.string() })
  }


  @Throws(IOException::class)
  fun getPosts(url: String): ReadableMap? {
    val map = WritableNativeMap();
    val result = run(url)
    val document = Jsoup.parse(result)
    val element = document.select("div.col-lg-8.col-sm-8.info-manga  div .row").first()
    map.putString("image",element.select(".col-md-4 div img").attr("src").trim())
    map.putString("title",element.select(".col-md-8 ul h1").text().trim())
    map.putString("title_jp",element.select(".col-md-8 ul li").eq(1).textNodes().last().text().replace(": ",""))
    map.putString("author",element.select(".col-md-8 ul li").eq(2).select("small a").text().trim())
    map.putArray("genre",parseGenre(element))
    map.putString("status",element.select(".col-md-8 ul li").eq(4).select("a").text().trim())
    map.putString("magazine",element.select(".col-md-8 ul li").eq(5).select("small a").text())
    map.putInt("view",element.select(".col-md-8 ul li").eq(6).textNodes().last().text().replace(": ","").toInt())
    map.putString("description",document.select("div.col-lg-8.col-sm-8.info-manga  div .row").last().select("p").text().trim())
    map.putArray("list",parseHTML(document.body()))
    return map
  }

  @Throws(IOException::class)
  fun getView(url:String): ReadableMap? {
    val map = WritableNativeMap()
    val result =  run(url)
    val document = Jsoup.parse(result)
    map.putString("next",document.select("ul.chapter_select option").prev().`val`())
    map.putString("prev",document.select("ul.chapter_select option").next().`val`())
    map.putArray("content",parseView(document.body()))
    return map;
  }

  private fun parseGenre(element: Element): ReadableArray? {
    val array = WritableNativeArray()
    for (elemenets in element.select(".col-md-8 ul li").eq(3).select("a")) {
      val map = WritableNativeMap()
      map.putString("id",elemenets.attr("href").trim())
      map.putString("title",elemenets.text().trim())
      array.pushMap(map)
    }
    return  array
  }

  private fun parseHTML(element: Element): ReadableArray? {
    val array = WritableNativeArray()
    for (elemenets in element.select("tbody tr")) {
      val map = WritableNativeMap();
      map.putString("id",elemenets.select(".chapter").attr("href").trim())
      map.putString("title",elemenets.select(".chapter b").text().trim())
      map.putString("time",elemenets.select("time").text().trim())
      array.pushMap(map)
    }
    return array;
  }

  private fun parseView(element: Element): ReadableArray? {
    val array = WritableNativeArray()
    for (elemenets in element.select(".chapter-img")) {
      array.pushString(elemenets.attr("data-src").trim())
    }
    return array
  }

}
