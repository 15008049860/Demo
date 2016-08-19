package com.example.sqliteopenhelperdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "demo_db";//数据库的名字
	private static final int DATABASEVERSION = 1;//版本号
	private static final String TABLE_NAME = "demo_table";//表名
	public static final String FIELD_id = "_id";//必须为_id
	public static final String FIELD_TEXT = "demo_text";
	private SQLiteDatabase db;//数据库
	private static final String TAG = "MyDataBase";

	public MyDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASEVERSION);
		// TODO Auto-generated constructor stub
		db = this.getWritableDatabase();// 打开或新建数据库(第一次时创建)获得SQLiteDatabase对象，为了读取和写入数据

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/* 建立表table */
		Log.i(TAG, " onCreate() ");
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + FIELD_id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + " " + FIELD_TEXT
				+ " TEXT)";
		db.execSQL(sql);
	}

	//更新数据库
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onUpgrade() ");
		//删除表的SQL
		String sql = "DROP TABLE IF EXITS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	//关闭数据库
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		Log.i(TAG, " close() ");
		db.close();
		super.close();
	}

	@Override
	public void onOpen(SQLiteDatabase db1) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onOpen() ");
		super.onOpen(db1);
	}

	// 增加数据
	public long addMethod(String str) {
		/* 将新增的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TEXT, str);//键值对
		long row = db.insert(TABLE_NAME, null, cv);
		Log.i(TAG, "addMethod row=" + row);
		return row;
	}

	// 删除
	public void deleteMethod(int id) {
		String[] whereArgs = { Integer.toString(id) };
		int rowsaffected = db.delete(TABLE_NAME, FIELD_id + "=?", whereArgs);
		Log.i(TAG, "deleteMethod() rowsaffected=" + rowsaffected);
	}

	// 修改
	public void modMethod(int id, String str) {
		ContentValues values = new ContentValues();
		values.put(FIELD_TEXT, str);
		String[] whereArgs = { Integer.toString(id) };
		int rowsaffected = db.update(TABLE_NAME, values, FIELD_id + " = ?",
				whereArgs);
		Log.i(TAG, "modMethod() rowsaffected=" + rowsaffected);
	}

	// 查询所有的数据 ，返回Cursor对象
	public Cursor query() {
		//asc是升序desc为降序（默认为asc）
		return db.query(TABLE_NAME, null, null, null, null, null,  FIELD_id + " ASC");
	}

}
