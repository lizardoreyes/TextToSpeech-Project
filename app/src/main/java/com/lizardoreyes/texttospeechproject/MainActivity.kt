package com.lizardoreyes.texttospeechproject

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lizardoreyes.texttospeechproject.databinding.ActivityMainBinding
import com.lizardoreyes.texttospeechproject.models.Language
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tts: TextToSpeech? = null
    val languages = arrayListOf(Language("English", "EN"), Language("Spanish", "ES"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTextToSpeech()
        initSpinnerLanguages()
        initListener()
    }

    private fun initListener() = with(binding) {
        btnPlay.setOnClickListener {
            tts?.speak(edtText.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun initTextToSpeech() = with(binding){
        val event = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val language = languages[spLanguage.selectedItemPosition].code
                tts?.language = Locale(language)
            } else {
                tvMessage.text = getString(R.string.message_not_support)
                edtText.isEnabled = false
                btnPlay.isEnabled = false
                spLanguage.isEnabled = false
                tvMessage.visibility = View.VISIBLE
            }
        }

        // Recommendation: Use Emulator Pixel 2 API 24 or physic telephone
        tts = TextToSpeech(applicationContext, event)
    }

    private fun initSpinnerLanguages() = with(binding) {
        spLanguage.adapter = ArrayAdapter(applicationContext, R.layout.item_adapter, R.id.tv_language, languages)

        spLanguage.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                tts?.language = Locale(languages[position].code)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}