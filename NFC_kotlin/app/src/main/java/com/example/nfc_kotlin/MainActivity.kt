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
import com.example.nfc_kotlin.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var TAG: String = "MainActivity"
    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcPendingIntent = PendingIntent.getActivity(this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }

    override fun onResume() {
        super.onResume()

        if(nfcAdapter != null){
            nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null)
            //filters, techLists가 null일 경우 모든 태그의 정보를 읽어 들이고 전송
        }
    }

    override fun onPause() {
        super.onPause()
        if(nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this)
        }

    }

    /** NFC Read */
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//
//        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED){
//            val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//            if (messages == null){
//                return
//            }
//
//            for (i in messages.indices){
//                showMsg(messages[i] as NdefMessage)
//            }
//        }
//    }
//
//    fun showMsg(mMessage: NdefMessage) {
//        Log.e("YMC", "NFC NdefMessage: ${mMessage}")
//        Log.e("YMC", "NFC NdefMessage: ${mMessage.toString()}")
//        val recs = mMessage.records
//        for (i in recs.indices) {
//            val record = recs[i]
//            if (Arrays.equals(record.type, NdefRecord.RTD_URI)) {
//                val u: Uri = record.toUri()
//                val j = Intent(Intent.ACTION_VIEW)
//                j.data = u
//                Log.e("YMC", "NFC ReadData: ${u} / recs.indices: ${i}")
//                Toast.makeText(applicationContext, "NFC ReadData: ${u}", Toast.LENGTH_SHORT).show()
////                startActivity(j)
////                finish()
//            }
//        }
//
//        Log.e("YMC", "recs2 : ${mMessage.byteArrayLength}")
//
//    }


    /** NFC Write */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if(intent != null){
            val detectedTag : Tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)!!
            val writeValue : String = "http://www.naver.com"
            val message: NdefMessage = createTagMessage(writeValue)

            writeTag(message, detectedTag)
        }
    }

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
                }
                if (ndef.maxSize < size) {
                    Toast.makeText(applicationContext, "NFC tag size too large", Toast.LENGTH_SHORT).show()
                }
                ndef.writeNdefMessage(message)
                Toast.makeText(applicationContext, "NFC tag is writted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.message?.let { Log.i(TAG, it) };
        }
    }
}