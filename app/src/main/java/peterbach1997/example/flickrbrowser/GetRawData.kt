package peterbach1997.example.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus() {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData(private val listener:OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private val TAG = "GetRawData"

    private var downloadStatus = DownloadStatus.OK

    interface OnDownloadComplete {
        fun onDownloadComplete(data:String, status: DownloadStatus)
    }
    //    Runs on the UI thread after doInBackground
    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute: called, params: $result")
        listener.onDownloadComplete(result, downloadStatus)
    }
    //    perform background thread
    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL specified"
        }
        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e:Exception){
           val error = when(e) {
                is MalformedURLException ->{
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: Invalid URL: ${e.message}"
                }
                is IOException ->{
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IOException: ${e.message}"
                }
                is SecurityException ->{
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Permission error: ${e.message}"
                }
               else ->{
                   downloadStatus = DownloadStatus.ERROR
                   "Unknown error"
               }
            }
            Log.d(TAG, "doInBackground: $error")
            return error
        }
    }


}