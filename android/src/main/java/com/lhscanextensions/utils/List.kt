package com.lhscanextensions.utils

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class List {

  private val api:Api = Api()

  @Throws(IOException::class)
  fun getData(url: String): ReadableMap {
    val map = WritableNativeMap()
    val html = api.run(url)
    print(html)
    val document = Jsoup.parse(html);
    map.putString("page",document.select(".btn-group button[class='btn btn-sm btn-info']").eq(1).text())
    map.putArray("list",parseArray(document.select(".row.top div .media")))
    return map
  }

  private fun parseArray(elemenet:Elements): ReadableArray {
      val array = WritableNativeArray()
      for (list in elemenet) {
          val map = WritableNativeMap();
          map.putString("id",list.select("a").attr("href"))
          map.putString("image",list.select("a img").attr("data-src"))
          map.putString("title",list.select(".media-body h3 a").text().trim())
          map.putInt("view",list.select(".media-body span").text().replace("\\D+".toRegex(),"").toInt())
          map.putArray("genre",puGenre(list));
          map.putString("chapter_title",list.select("div a").text().trim())
          map.putString("chapter_id",list.select("div a").attr("href").trim())
          array.pushMap(map)
      }
      return array;
  }

  private fun puGenre(element: Element): ReadableArray? {
    val array = WritableNativeArray()
    for (elements in element.select(".media-body small a")) {
      val map = WritableNativeMap()
      map.putString("id",elements.attr("href").trim())
      map.putString("title",elements.text().trim())
      array.pushMap(map)
    }
    return  array
  }

}
