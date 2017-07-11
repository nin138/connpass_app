package item.java_conf.gr.jp.connpass_viewer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import item.java_conf.gr.jp.connpass_viewer.entity.Series


val DB = "connpass_db"
val DB_VERSION = 1
val CREATE_TABLE = "create table setting ( _id integer primary key autoincrement, data integer not null );"

val CREATE_BLACK_LIST = "CREATE TABLE black_list(" +
    "  id int PRIMARY KEY," +
    "  title text," +
    "  url text" +
    ");"

class SQLite(context: Context) : SQLiteOpenHelper(context, DB, null, DB_VERSION) {
  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(CREATE_BLACK_LIST)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  }
  fun addBlackList(id: Int, title: String, url: String) {
    writableDatabase.execSQL("INSERT INTO black_list VALUES (?,?,?)", arrayOf(id.toString(), title, url))
    writableDatabase.close()
    Setting.blackList.add(id)
  }
  fun getBlackList(): List<Series> {
    val cursor = readableDatabase.rawQuery("SELECT id, title, url FROM black_list", null)
    val list = ArrayList<Series>()
    val id_num = cursor.getColumnIndex("id")
    val title_num = cursor.getColumnIndex("title")
    val url_num = cursor.getColumnIndex("url")
    while (cursor.moveToNext()) {
      list.add(Series(cursor.getInt(id_num), cursor.getString(title_num), cursor.getString(url_num)))
    }
    cursor.close()
    readableDatabase.close()
    return list
  }
  fun removeBlackList(id: Int) {
    writableDatabase.execSQL("DELETE FROM black_list WHERE id=?", arrayOf(id))
    Setting.blackList.remove(id)
  }
}