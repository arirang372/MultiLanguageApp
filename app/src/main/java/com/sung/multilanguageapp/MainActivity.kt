package com.sung.multilanguageapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

/**
 *   MainActivity that demonstrates the multi language used in the app.
 *
 *   @author John Sung
 */
class MainActivity : AppCompatActivity() {
    var con: Context? = null
    private lateinit var ivLangFlag: ImageView
    var prefData: PrefData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        con = applicationContext
        prefData = PrefData(con)
        setToolbar()
        setFloatingActionBar()
        ivLangFlag = findViewById<View>(R.id.ivLangFlag) as ImageView
        setLangFlagImage()
    }

    private fun setLangFlagImage() {
        when (prefData?.getCurrentLanguage()) {
            "eng" -> ivLangFlag.setImageResource(R.drawable.en)
            "bn" -> ivLangFlag.setImageResource(R.drawable.bn)
            "de" -> ivLangFlag.setImageResource(R.drawable.de)
            "fr" -> ivLangFlag.setImageResource(R.drawable.fr)
            else -> {}
        }
    }

    private fun setToolbar() {
        val toolbar: MaterialToolbar = findViewById<View>(R.id.toolbar) as MaterialToolbar
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun setFloatingActionBar() {
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            sendMail()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.lang_select_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.eng -> setLanguage("eng")
            R.id.bn -> setLanguage("bn")
            R.id.fr -> setLanguage("fr")
            R.id.de -> setLanguage("de")
            R.id.kr -> setLanguage("kr")
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLanguage(language: String) {
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(language.toLowerCase()))
        } else {
            config.locale = Locale(language.toLowerCase())
        }
        resources.updateConfiguration(config, dm)

        //store current language in prefrence
        prefData?.setCurrentLanguage(language)

        //With new configuration start activity again
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun sendMail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, "johnsung419@gmail.com")
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // reset
        prefData?.setCurrentLanguage("eng")
    }
}