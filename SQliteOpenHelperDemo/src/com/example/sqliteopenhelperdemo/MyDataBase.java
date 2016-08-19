package com.example.sqliteopenhelperdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "demo_db";//���ݿ������
	private static final int DATABASEVERSION = 1;//�汾��
	private static final String TABLE_NAME = "demo_table";//����
	public static final String FIELD_id = "_id";//����Ϊ_id
	public static final String FIELD_TEXT = "demo_text";
	private SQLiteDatabase db;//���ݿ�
	private static final String TAG = "MyDataBase";

	public MyDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASEVERSION);
		// TODO Auto-generated constructor stub
		db = this.getWritableDatabase();// �򿪻��½����ݿ�(��һ��ʱ����)���SQLiteDatabase����Ϊ�˶�ȡ��д������

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/* ������table */
		Log.i(TAG, " onCreate() ");
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + FIELD_id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + " " + FIELD_TEXT
				+ " TEXT)";
		db.execSQL(sql);
	}

	//�������ݿ�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onUpgrade() ");
		//ɾ�����SQL
		String sql = "DROP TABLE IF EXITS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	//�ر����ݿ�
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

	// ��������
	public long addMethod(String str) {
		/* ��������ֵ����ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(FIELD_TEXT, str);//��ֵ��
		long row = db.insert(TABLE_NAME, null, cv);
		Log.i(TAG, "addMethod row=" + row);
		return row;
	}

	// ɾ��
	public void deleteMethod(int id) {
		String[] whereArgs = { Integer.toString(id) };
		int rowsaffected = db.delete(TABLE_NAME, FIELD_id + "=?", whereArgs);
		Log.i(TAG, "deleteMethod() rowsaffected=" + rowsaffected);
	}

	// �޸�
	public void modMethod(int id, String str) {
		ContentValues values = new ContentValues();
		values.put(FIELD_TEXT, str);
		String[] whereArgs = { Integer.toString(id) };
		int rowsaffected = db.update(TABLE_NAME, values, FIELD_id + " = ?",
				whereArgs);
		Log.i(TAG, "modMethod() rowsaffected=" + rowsaffected);
	}

	// ��ѯ���е����� ������Cursor����
	public Cursor query() {
		//asc������descΪ����Ĭ��Ϊasc��
		return db.query(TABLE_NAME, null, null, null, null, null,  FIELD_id + " ASC");
	}

}
