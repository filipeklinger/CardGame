package br.com.si.ufrrj.logica

import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import br.com.si.ufrrj.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley


class VolleySingleton constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: VolleySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleySingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    fun getImageLoader():ImageLoader{

        return ImageLoader(
            requestQueue,
            object : ImageLoader.ImageCache {
                private val mCache: LruCache<String, Bitmap> =
                    LruCache(10)

                override fun getBitmap(url: String): Bitmap? {
                    return mCache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    mCache.put(url, bitmap)
                }
            })
    }
}