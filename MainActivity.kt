package ai.paul.app

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.webkit.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
 private lateinit var web: WebView
 private var fileCallback: ValueCallback<Array<android.net.Uri>>? = null
 private val filePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
  val d=r.data
  val u=if(r.resultCode==Activity.RESULT_OK && d!=null) {
   if(d.clipData!=null) Array(d.clipData!!.itemCount){i->d.clipData!!.getItemAt(i).uri}
   else d.data?.let{arrayOf(it)}
  } else null
  fileCallback?.onReceiveValue(u); fileCallback=null
 }
 private val speech = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
  val t=if(r.resultCode==Activity.RESULT_OK) r.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull().orEmpty() else ""
  web.evaluateJavascript("window.onPaulSpeechResult(${org.json.JSONObject.quote(t)})",null)
 }
 private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { if(it) launchSpeech() }
 override fun onCreate(savedInstanceState:Bundle?) {
  super.onCreate(savedInstanceState)
  web=WebView(this)
  web.settings.javaScriptEnabled=true; web.settings.domStorageEnabled=true
  web.settings.allowFileAccess=false
  web.webViewClient=WebViewClient()
  web.webChromeClient=object:WebChromeClient(){
   override fun onShowFileChooser(v:WebView?, cb:ValueCallback<Array<android.net.Uri>>?, p:FileChooserParams?):Boolean {
    fileCallback?.onReceiveValue(null); fileCallback=cb
    filePicker.launch(p?.createIntent() ?: Intent(Intent.ACTION_GET_CONTENT).setType("*/*").addCategory(Intent.CATEGORY_OPENABLE)); return true
   }
  }
  web.addJavascriptInterface(object { @JavascriptInterface fun startVoiceInput(){ runOnUiThread {
   if(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED) launchSpeech()
   else permission.launch(Manifest.permission.RECORD_AUDIO)
  } } },"PaulNative")
  setContentView(web); web.loadUrl("file:///android_asset/index.html")
 }
 private fun launchSpeech(){ try {
  val i=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
   putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
   putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault()); putExtra(RecognizerIntent.EXTRA_PROMPT,"Parle à PAUL AI")
  }; speech.launch(i)
 } catch(_:Exception){} }
}
