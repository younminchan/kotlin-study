package com.example.firebase_cloud_firestore_kotlin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_cloud_firestore_kotlin.data.FirebaseData
import com.example.firebase_cloud_firestore_kotlin.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private var sampleNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initCloudFirestore()
    }

    private fun initCloudFirestore() {
        db = Firebase.firestore

        //초기값 설정
        setDocument(
            FirebaseData(
                sampleName = "firstData",
                sampleNumber = sampleNumber,
                sampleBoolean = false,
                createdAt = com.google.firebase.Timestamp.now()
            )
        )

        binding.buttonSet.setOnClickListener {
            sampleNumber++
            setDocument(
                FirebaseData(
                    sampleName = "메세지$sampleNumber",
                    sampleNumber = sampleNumber,
                    sampleBoolean = true,
                    createdAt = com.google.firebase.Timestamp.now()
                )
            )
        }

        binding.buttonGet.setOnClickListener {

            /** Ex1. */
//            db.collection("sampleCollection")
//                .whereEqualTo("sampleName", "sampleData1")
////                .whereEqualTo("sampleName", "sampleData2")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        Log.e("YMC", "Get결과값: ${document.data}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.e("YMC", "Error getting documents: ", exception)
//                }

            /** Ex2. */
//            db.collection("sampleCollection")
//                .whereEqualTo("sampleName", "sampleData1")
////                .whereEqualTo("sampleName", "sampleData2")
//                .get()
//                .addOnCompleteListener { result ->
//                    if(result.isSuccessful)
//                    for (document in result.result.documents) {
//                        Log.e("YMC", "Get결과값: ${document.data}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.e("YMC", "Error getting documents: ", exception)
//                }

            /** Ex3. */
            db.collection("sampleCollection")
                //.whereIn("sampleName", listOf("sampleData1", "sampleData2"))
//                .whereEqualTo("sampleName", "sampleData2")
                //.orderBy("createdAt", Query.Direction.ASCENDING).limit(1000)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    Log.e("YMC", "result: $result")
                    Log.e("YMC", "result.documents: ${result.documents}")
                    for (document in result.documents) {
                        Log.e("YMC", "Get결과값: ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("YMC", "Error getting documents: ", exception)
                }
        }


        /** Ex4. - Snapshot(실시간 데이터 처리) */
        db.collection("sampleCollection").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            for(item in querySnapshot!!.documentChanges){
                when(item.type){
                    DocumentChange.Type.ADDED -> {
                        Log.e("YMC", "DocumentChange.Type.ADDED: ${item.document}")
                        //realTimeArrayList.add(item.document.toObject(UserDTO::class.java))
                        //realTimeKeyArrayList.add(item.document.id)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        Log.e("YMC", "DocumentChange.Type.MODIFIED: ${item.document}")
                        //modifyItem(item.document.id, item.document.toObject(UserDTO::class.java))
                    }
                    DocumentChange.Type.REMOVED -> {
                        Log.e("YMC", "DocumentChange.Type.REMOVED: ${item.document}")
                        //deleteItem(item.document.id)
                    }
                    else -> {}
                }
            }
            //recyclerview_read_database_realtime.adapter.notifyDataSetChanged()
        }

    }

    private fun setDocument(data: FirebaseData) {
        FirebaseFirestore.getInstance()
            .collection("sampleCollection")
            .document(data.sampleName)
            .set(data)
            .addOnSuccessListener {
                binding.textResult.text = "success!"
                toastMsg("$data 데이터 입력 성공!")
                Log.e("YMC", "$data 데이터 입력 성공!")
            }
            .addOnFailureListener {
                binding.textResult.text = "fail!"
                toastMsg("$data 데이터 입력 실패!")
                Log.e("YMC", "$data 데이터 입력 실패!")
            }
    }

    /** 토스트 메세지(중복방지) */
    var toast: Toast? = null
    fun toastMsg(string: String) {
        try {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(Runnable {
                toast?.cancel()
                toast = Toast.makeText(this, string, Toast.LENGTH_SHORT)
                toast?.show()
            }, 0)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}