package item.java_conf.gr.jp.connpass_viewer

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import item.java_conf.gr.jp.connpass_viewer.entity.ConnpassResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection



class Http : AsyncTask<String, Void, ConnpassResponse?>() {

  interface Callback {
    fun onSuccess(body: ConnpassResponse)
    fun onError()
  }
  private var callback: Callback? = null
  fun setCallback(cb: Callback) {
    callback = cb
  }

  override fun doInBackground(vararg args: String?): ConnpassResponse? {
    val url: URL = URL(args[0])
    try {
      val connection = url.openConnection() as HttpURLConnection
      connection.connectTimeout = 10000
      connection.readTimeout = 10000
      connection.connect()
      val status = connection.responseCode
      if(status == HttpsURLConnection.HTTP_OK) {
        Log.d("http", "ok")
        var encoding = connection.contentEncoding
        if (encoding == null) {
          encoding = "UTF-8"
        }
        Setting.encording = encoding
        val streamReader = InputStreamReader(connection.inputStream, encoding)
        val buff = BufferedReader(streamReader)
        val sb = StringBuffer()
        var line: String? = buff.readLine()
        while(line != null) {
          sb.append(line)
          line = buff.readLine()
        }
        buff.close()
        streamReader.close()
        connection.inputStream.close()
        val jsonString = sb.toString()
        val gson = Gson()
        val json = gson.fromJson(jsonString, ConnpassResponse::class.java)
        return json
      }
    } catch (e: Exception) {
      e.printStackTrace()
      System.out.println(e.message)
    }
    return null
  }

  override fun onPostExecute(result: ConnpassResponse?) {
    super.onPostExecute(result)
    if(result != null) callback?.onSuccess(result)
    else callback?.onError()
  }

}