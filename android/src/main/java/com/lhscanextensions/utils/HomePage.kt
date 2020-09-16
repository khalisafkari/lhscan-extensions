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

class HomePage {

  val client = OkHttpClient()

  @Throws(IOException::class)
  private fun run(url: String):String? {
    val request = Request.Builder()
      .url(url)
      .addHeader("referer","https://loveheaven.net")
      .addHeader("x-app","com.dev.lhscan")
      .build()
    client.newCall(request).execute().use({ response -> return response.body()?.string() })
  }


  @Throws(IOException::class)
  fun get(): ReadableArray? {
    val array = WritableNativeArray()
    val result = run("https://loveheaven.net")
    val document = Jsoup.parse(result);
    val elements = document.select(".doreamon .itemupdate")
    for (element in elements) {
      array.pushMap(parseHTMLResult(element))
    }
    return array;
  }

  private fun parseHTMLResult(element: Element): ReadableMap? {
    val map = WritableNativeMap();
    map.putString("id",element.select("a").attr("href").trim())
    map.putString("image",element.select("a img").attr("data-src").trim())
    map.putString("title",element.select(".group .title-h3-link h3").text().trim())
    map.putString("chapter_id",element.select(".group .chapter").attr("href").trim())
    map.putString("chapter_title",element.select(".group .chapter").text().trim())
    map.putInt("view",element.select(".group .view").text().toInt())
    map.putString("time",element.select(".group time").text().trim())
    return map;
  }
}
