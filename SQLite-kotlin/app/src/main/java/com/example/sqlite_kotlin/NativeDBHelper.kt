package com.example.sqlite_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NativeDBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String =
            "CREATE TABLE if not exists tb_sqlite (" +
                    "f_idx INTEGER primary key autoincrement," +
                    "f_key VARCHAR(255)," +
                    "f_value VARCHAR(255));"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists tb_sqlite"
        db?.execSQL(sql)
        onCreate(db)
    }
}

/** 테스트 코드 */
class DBTestHelper(var db: SQLiteDatabase) {
    fun insert(key: String, value: String) {
        var query = "INSERT INTO tb_sqlite('f_key', 'f_value') values(\'${key}\', \'${value}\');"
        db.execSQL(query)
    }

    @SuppressLint("Range")
    fun select(): ArrayList<String> {
        var query = "SELECT * FROM tb_sqlite;"
        var result: Cursor = db.rawQuery(query, null)
        var list = ArrayList<String>()

        while (result.moveToNext()) {
            var data = "f_idx: ${result.getString(result.getColumnIndex("f_idx"))} / " +
                    "f_key: ${result.getString(result.getColumnIndex("f_key"))} / " +
                    "f_value: ${result.getString(result.getColumnIndex("f_value"))}"

            list.add(data)
        }
        result.close()
        return list
    }

    fun delete() {
        db.execSQL("DELETE FROM tb_sqlite WHERE f_idx <= 10")
    }
}