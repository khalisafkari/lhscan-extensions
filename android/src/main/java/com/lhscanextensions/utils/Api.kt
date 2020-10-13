package com.lhscanextensions.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class Api {

  private val client:OkHttpClient = OkHttpClient()

  @Throws(IOException::class)
  fun run(url: String ): String? {
      val request = Request.Builder()
        .addHeader("referer","https://loveheaven.net")
        .addHeader("x-app","com.dev.lhscan")
        .url(url).build();
      val response = client.newCall(request).execute()
      return  response.body()?.string();
  }



}
