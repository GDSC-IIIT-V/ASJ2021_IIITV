package com.asj.iiitv_asj

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.asj.iiitv_asj.utility.BgMediaWebView

class TopivViewActivity : AppCompatActivity() {

    private lateinit var topicName:String
    private lateinit var yogaVideo: BgMediaWebView
    private lateinit var progressBar: ProgressBar
    private lateinit var videoUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topiv_view)

        val intent = intent
        topicName = intent.getStringExtra("tName").toString()
        videoUrl = intent.getStringExtra("videoUrl").toString()

        val actionBar = supportActionBar
        actionBar!!.title = topicName

        progressBar = findViewById(R.id.videoLoadingProgrssbar)
        yogaVideo = findViewById(R.id.yogaWebView)

    }

    private fun loadVid(vid: String) {
        yogaVideo.webChromeClient = MyChrome()
        yogaVideo.settings.pluginState = WebSettings.PluginState.ON
        yogaVideo.webViewClient = MyWebViewClient()
        yogaVideo.settings
        yogaVideo.settings.javaScriptEnabled = true
        yogaVideo.setBackgroundColor(0x00000000)
        yogaVideo.keepScreenOn = true
        yogaVideo.isHorizontalScrollBarEnabled = false
        yogaVideo.isVerticalScrollBarEnabled = false
        yogaVideo.settings.builtInZoomControls = true
        val mimeType = "text/html"
        val encoding = "UTF-8"
        val html = getHTML(vid)
        Toast.makeText(applicationContext,vid,Toast.LENGTH_SHORT).show()
        yogaVideo.loadDataWithBaseURL("", html, mimeType, encoding, "")
    }

    fun checkForLink(link: String): String? {
        var link = link
        if (link.startsWith("https://youtube.com/playlist?list=")) {
            link = "" + link.substring(link.indexOf('=') + 1)
        } else if (link.startsWith("https://youtu.be/")) {
            link = "" + link.substring(link.lastIndexOf('/') + 1)
        }
        return link
    }

    fun getHTML(vid: String): String {
        return ("<html>"
                + "<head>"
                + "</head>"
                + "<body style=\"border: 0; padding: 0\">"
                + "<iframe "
                + "type=\"text/html\" "
                + "class=\"youtube-player\" "
                + "width= 100%\""
                + "\" "
                + "height= 100%\""
                + "\" "
                + "src=\"http://www.youtube.com/embed/"
                + vid
                + "?controls=1&showinfo=0&showsearch=0&modestbranding=0" +
                "&autoplay=1&fs=1&vq=small\" " + "frameborder=\"0\" allow=\"autoplay;encrpted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"
                + "</body>"
                + "</html>")
    }

    override fun onStart() {
        super.onStart()
        loadVid(checkForLink(videoUrl)!!)
    }

    override fun onPause() {
        super.onPause()
        yogaVideo.loadUrl("")
        yogaVideo.clearCache(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        yogaVideo.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        yogaVideo.restoreState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            if (progressBar.getVisibility() == View.GONE) {
                progressBar.setVisibility(View.VISIBLE)
            }
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE)
            }
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            if (progressBar.visibility == View.GONE) {
                progressBar.visibility = View.VISIBLE
            }
            super.onPageStarted(view, url, favicon)
        }
    }


    private inner class MyChrome() : WebChromeClient() {
        private var mCustomView: View?=null
        private var mCustomViewCallback: CustomViewCallback? = null
        private lateinit var mFullscreenContainer: FrameLayout
        private var mOriginalOrientation = 0
        private var mOriginalSystemUiVisibility = 0
        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomView == null) {
                null
            } else BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573)
        }

        override fun onHideCustomView() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            (getWindow().getDecorView() as FrameLayout).removeView(mCustomView)
            mCustomView = null
            getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility)
            //setRequestedOrientation(this.mOriginalOrientation);
            mCustomViewCallback!!.onCustomViewHidden()
            mCustomViewCallback = null
        }

        override fun onShowCustomView(
            paramView: View,
            paramCustomViewCallback: CustomViewCallback
        ) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            yogaVideo.setLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            mCustomView = paramView
            mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility()
            mOriginalOrientation = getRequestedOrientation()
            mCustomViewCallback = paramCustomViewCallback
            (getWindow().getDecorView() as FrameLayout).addView(
                mCustomView,
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            getWindow().getDecorView().setSystemUiVisibility(3846)
        }
    }

}