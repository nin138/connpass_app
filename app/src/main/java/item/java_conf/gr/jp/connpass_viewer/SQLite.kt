package item.java_conf.gr.jp.connpass_viewer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val DB = "connpass_db"
val DB_VERSION = 1
val CREATE_TABLE = "create table setting ( _id integer primary key autoincrement, data integer not null );"

private class MySQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DB, null, DB_VERSION) {
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(CREATE_TABLE)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  }
}