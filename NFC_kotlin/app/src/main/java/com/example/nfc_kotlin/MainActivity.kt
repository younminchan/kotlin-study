package com.example.nfc_kotlin

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.example.nfc_kotlin.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var TAG: String = "MainActivity"
    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter
    private var tagMode = ""
    private var nfcEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcPendingIntent = PendingIntent.getActivity(this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

        initClick()
    }

    override fun onResume() {
        super.onResume()
        Log.e("YMC", "onResume nfcEnable: ${nfcEnable}")
        if(!nfcEnable){
            changeStateNFC(true)
        }
    }

    override fun onPause() {
        super.onPause()
        changeStateNFC(false)
    }

    private fun initClick(){
        Log.e("YMC", "initClick nfcEnable: ${nfcEnable}")

        //Write
        binding.bWrite.setOnClickListener {
            tagMode = "write"
            Toast.makeText(this, "NFC write실행!", Toast.LENGTH_SHORT).show()
            binding.tvRead.text = "NFC write 대기중"
            if(!nfcEnable){
                changeStateNFC(true)
            }
        }

        //Read
        binding.bRead.setOnClickListener {
            tagMode = "read"
            Toast.makeText(this, "NFC read실행!", Toast.LENGTH_SHORT).show()
            binding.tvRead.text = "NFC read 대기중"
            if(!nfcEnable){
                changeStateNFC(true)
            }
        }
    }

    /** NFC 상태변경 */
    private fun changeStateNFC(boolean: Boolean) {
        try {
            nfcEnable = boolean
            if (boolean) {
                nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null)
            } else {
                nfcAdapter.disableForegroundDispatch(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //같은화면에서 같은화면이 호출되었을 때
    /** TODO: NFC intent */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.e("YMC", "onNewIntent: ${intent.action} tagMode: ${tagMode}")

        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED){

            //TODO: Read? Write?
            when(tagMode){
                "write" -> {
                    val detectedTag: Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)!!
                    val writeValue: String = "http://www.naver.com/test"
                    val message: NdefMessage = createTagMessage(writeValue)
                    writeTag(message, detectedTag)
                }
                "read" -> {
                    val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    messages?.let {
                        for (i in messages.indices){
                            readTag(messages[i] as NdefMessage)
                        }
                    }
                }
            }
        }
    }
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//
//        Log.e("YMC", "onNewIntent / intent.action: ${intent.action}")
//        if(intent != null){
//            val detectedTag : Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)!!
//            val writeValue : String = "http://www.naver.com"
//            val message: NdefMessage = createTagMessage(writeValue)
//
//            writeTag(message, detectedTag)
//        }
//    }


    /** NFC Read */
    private fun readTag(mMessage: NdefMessage) {
        Log.e("YMC", "NFC NdefMessage: ${mMessage}")
        val recs = mMessage.records
        for (i in recs.indices) {
            val record = recs[i]
            if (Arrays.equals(record.type, NdefRecord.RTD_URI)) {
                val u: Uri = record.toUri()
                Log.e("YMC", "NFC ReadData: ${u} / recs.indices: ${i}")
                Toast.makeText(applicationContext, "NFC ReadData: ${u}", Toast.LENGTH_SHORT).show()
                binding.tvRead.text = "$u"
            }
        }
        Log.e("YMC", "recs2 : ${mMessage.byteArrayLength}")
    }

    /** NFC Write */
    private fun createTagMessage(msg: String): NdefMessage {
        return NdefMessage(NdefRecord.createUri(msg))
    }

    fun writeTag(message: NdefMessage, tag: Tag) {
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    Toast.makeText(applicationContext, "can not write NFC tag", Toast.LENGTH_SHORT).show()
                    binding.tvRead.text = "can not write NFC tag"
                }
                if (ndef.maxSize < size) {
                    Toast.makeText(applicationContext, "NFC tag size too large", Toast.LENGTH_SHORT).show()
                    binding.tvRead.text = "NFC tag size too large"
                }
                ndef.writeNdefMessage(message)
                Toast.makeText(applicationContext, "NFC tag is writted", Toast.LENGTH_SHORT).show()
                binding.tvRead.text = "NFC tag is writted"
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.i(TAG, it)
            }
        }
    }
}