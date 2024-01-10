package com.example.firebase_cloud_firestore_kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_cloud_firestore_kotlin.data.MessageItem
import com.example.firebase_cloud_firestore_kotlin.databinding.ActivityMainBinding
import com.example.firebase_cloud_firestore_kotlin.util.FirestoreValue
import com.example.firebase_cloud_firestore_kotlin.util.SharedPreferencesManager
import com.example.firebase_cloud_firestore_kotlin.util.Utils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCloudFirestore()
        clickListener()
        //testCode()
    }

    /** init */
    private fun initCloudFirestore() {
        SharedPreferencesManager.init(this)
        db = Firebase.firestore

        binding.etCollectionName.setText(SharedPreferencesManager.getString(FirestoreValue.collectionName, "myChat"))
        binding.etMyName.setText(SharedPreferencesManager.getString(FirestoreValue.myName, "user1"))
    }

    private fun clickListener() {
        /** Talk Activity로 이동 */
        binding.moveAct.setOnClickListener {
            var collectionName = "${binding.etCollectionName.text}"
            var myName = "${binding.etMyName.text}"

            if (collectionName.isNullOrEmpty()) {
                Utils.toastMsg(this, "채팅방 이름을 입력해주세요")
                return@setOnClickListener
            }
            if (myName.isNullOrEmpty()) {
                Utils.toastMsg(this, "유저 이름을 입력해주세요")
                return@setOnClickListener
            }

            SharedPreferencesManager.putString(FirestoreValue.collectionName, collectionName)
            SharedPreferencesManager.putString(FirestoreValue.myName, myName)

            startActivity(Intent(this, TalkActivity::class.java))
            //finish()
        }
    }

    private fun setDocument(data: MessageItem) {
        FirebaseFirestore.getInstance()
            .collection("sampleCollection")
            .document(data.name)
            .set(data)
            .addOnSuccessListener {
                Utils.toastMsg(this, "$data 데이터 입력 성공!")
                Log.e("YMC", "$data 데이터 입력 성공!")
            }
            .addOnFailureListener {
                Utils.toastMsg(this, "$data 데이터 입력 실패!")
                Log.e("YMC", "$data 데이터 입력 실패!")
            }
    }

    private fun testCode() {
        /** Ex1. */
//        db.collection("sampleCollection")
//            .whereEqualTo("sampleName", "sampleData1")
////                .whereEqualTo("sampleName", "sampleData2")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.e("YMC", "Get결과값: ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("YMC", "Error getting documents: ", exception)
//            }

        /** Ex2. */
//        db.collection("sampleCollection")
//            .whereEqualTo("sampleName", "sampleData1")
////                .whereEqualTo("sampleName", "sampleData2")
//            .get()
//            .addOnCompleteListener { result ->
//                if (result.isSuccessful)
//                    for (document in result.result.documents) {
//                        Log.e("YMC", "Get결과값: ${document.data}")
//                    }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("YMC", "Error getting documents: ", exception)
//            }

        /** Ex3. */
//        db.collection("myChat")
//            //.whereIn("sampleName", listOf("sampleData1", "sampleData2"))
//            //.whereEqualTo("sampleName", "sampleData2")
//            //.orderBy("createdAt", Query.Direction.ASCENDING).limit(1000)
//            .orderBy("createdAt", Query.Direction.DESCENDING)
//            .get()
//            .addOnSuccessListener { result ->
//                Log.e("YMC", "result: $result")
//                Log.e("YMC", "result.documents: ${result.documents}")
//                for (document in result.documents) {
//                    Log.e("YMC", "Get결과값: ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("YMC", "Error getting documents: ", exception)
//            }

        /** Ex4. - Snapshot(실시간 데이터 처리) */
//        db.collection("myChat").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//            for (item in querySnapshot!!.documentChanges) {
//                when (item.type) {
//                    DocumentChange.Type.ADDED -> {
//                        //Log.e("YMC", "DocumentChange.Type.ADDED: ${item.document}")
//                        //realTimeArrayList.add(item.document.toObject(UserDTO::class.java))
//                        //realTimeKeyArrayList.add(item.document.id)
//                    }
//                    DocumentChange.Type.MODIFIED -> {
//                        //Log.e("YMC", "DocumentChange.Type.MODIFIED: ${item.document}")
//                        //modifyItem(item.document.id, item.document.toObject(UserDTO::class.java))
//                    }
//                    DocumentChange.Type.REMOVED -> {
//                        //Log.e("YMC", "DocumentChange.Type.REMOVED: ${item.document}")
//                        //deleteItem(item.document.id)
//                    }
//                    else -> {}
//                }
//            }
//        }
    }
}