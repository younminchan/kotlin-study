package com.example.firebase_cloud_firestore_kotlin

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase_cloud_firestore_kotlin.data.MessageItem
import com.example.firebase_cloud_firestore_kotlin.databinding.ActivityTalkBinding
import com.example.firebase_cloud_firestore_kotlin.util.FirestoreValue
import com.example.firebase_cloud_firestore_kotlin.util.SharedPreferencesManager
import com.example.firebase_cloud_firestore_kotlin.util.Utils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class TalkActivity : AppCompatActivity() {
    lateinit var binding: ActivityTalkBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var messageAdapter: MessageAdapter
    var messageList = arrayListOf<MessageItem>()

    lateinit var chatDataList: CollectionReference

    //TODO: 채팅방 이름(Firestore에 Collcetion Name을 입력해줘야함)
    private var chatName = SharedPreferencesManager.getString(FirestoreValue.collectionName, "") //TODO: 채팅방 이름
    private var userName = SharedPreferencesManager.getString(FirestoreValue.myName, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalkBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initTalk()
    }

    private fun initTalk() {
        db = Firebase.firestore
        chatDataList = db.collection(chatName)
        messageAdapter = MessageAdapter(this, userName, messageList)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@TalkActivity, LinearLayoutManager.VERTICAL, false)
            adapter = messageAdapter
        }

        /** 메세지 최신화 */
        chatDataList.addSnapshotListener { querySnapshot, error ->
            try {
                for (item in querySnapshot!!.documentChanges) {
                    when (item.type) {
                        DocumentChange.Type.ADDED -> {
                            //Log.e("YMC", "DocumentChange.Type.ADDED: ${item.document.data}")

                            //아이템 추가
                            messageList.add(item.document.toObject(MessageItem::class.java))
                            //데이터 변경 Adapter notify
                            messageAdapter.notifyItemInserted(messageList.size-1)
                            //최하단으로 Scroll
                            Handler().postDelayed({
                                binding.recycler.scrollToPosition(messageList.size-1)
                            }, 100)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            Log.e("YMC", "DocumentChange.Type.MODIFIED: ${item.document.data}")
                            //modifyItem(item.document.id, item.document.toObject(UserDTO::class.java))
                        }
                        DocumentChange.Type.REMOVED -> {
                            Log.e("YMC", "DocumentChange.Type.REMOVED: ${item.document.data}")
                            //deleteItem(item.document.id)
                        }
                        else -> {}
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /** 메세지 전송 */
        binding.btn.setOnClickListener {
            if (binding.et.text.isNullOrEmpty()) {
                Utils.toastMsg(this, "메세지를 입력해주세요.")
                return@setOnClickListener
            }

            var message = "${binding.et.text}"
            val calendar: Calendar = Calendar.getInstance()
            val time: String = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE))

            var insertItem = MessageItem(name = userName, message = message, time = time)

            //Firestore 데이터 추가
            chatDataList.document("MSG_" + System.currentTimeMillis()).set(insertItem)

            //EditText clear
            binding.et.text?.clear()

            //키보드 내리기
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
        }
    }
}