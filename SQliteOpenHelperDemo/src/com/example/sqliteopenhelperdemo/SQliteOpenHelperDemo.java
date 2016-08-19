package com.example.sqliteopenhelperdemo;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

public class SQliteOpenHelperDemo extends Activity {
	
	private EditText mEditText;
	private ListView mListView;
	private SimpleCursorAdapter adapter;
	private MyDataBase dataBase;
	private  Cursor mCursor;
	private int _id;//当前游标Cursor所在的字段值

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_open_helper_demo);

		mEditText = (EditText) this.findViewById(R.id.edit_id);
		mListView = (ListView) this.findViewById(R.id.listView_id);
		mListView.setOnItemClickListener(clickListener);
		mListView.setOnItemSelectedListener(selectedListener);
		
		//创建SQLiteOpenHelper对象的引用
		dataBase=new MyDataBase(this);
	    /* 取得DataBase里的资料 */
		mCursor=dataBase.query();
	    /* new SimpleCursorAdapter并将myCursor传入，显示数据的字段为   _id，todo_text */ 
		adapter=new SimpleCursorAdapter(
				this,
				R.layout.list,
				mCursor, 
				new String[]{
						MyDataBase.FIELD_id,
						MyDataBase.FIELD_TEXT
		       },
		        new int[]{
						R.id.itemid_id,
						R.id.itemcon_id,						
				});
		mListView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater()
				.inflate(R.menu.activity_sqlite_open_helper_demo, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			this.add();
			break;
		case R.id.menu_modfi:
			this.mod();
			break;
		case R.id.menu_delete:
            this.delete();
			break;

		default:
			break;
		}
		return false;

	};
   // ListView的点击事件处理
	OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			mCursor.moveToPosition(arg2);
			_id=mCursor.getInt(mCursor.getColumnIndex(MyDataBase.FIELD_id));
			mEditText.setText(mCursor.getString(mCursor.getColumnIndex(MyDataBase.FIELD_TEXT)));
		}

	};
	//监听处理鼠标滚轮的事件处理
	OnItemSelectedListener selectedListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			_id=mCursor.getInt(mCursor.getColumnIndex(MyDataBase.FIELD_id));
			SQLiteCursor  c=(SQLiteCursor)arg0.getSelectedItem();
			mEditText.setText(c.getString(c.getColumnIndex(MyDataBase.FIELD_TEXT)));
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	};

	//更新ListView
	public void  myUpDateShow()
	{
		         /* 重新查询 */
				mCursor.requery();
				//告知适配器已经改变
				adapter.notifyDataSetChanged();
				mEditText.setText("");
				_id = 0; 
	}
	//增加
	public void add()
	{
		String str=mEditText.getText().toString();
		if (str.equalsIgnoreCase("")) {
			return;
		}
		 /* 新增数据到数据库 */
		dataBase.addMethod(str);
		myUpDateShow();
	}
	//修改
	public void mod()
	{
		String str=mEditText.getText().toString();
		if (str.equalsIgnoreCase("")) {
			return;
		}
		//修改
		dataBase.modMethod(_id,str);
		myUpDateShow();
	}
	//删除
	public void delete()
	{
		if (_id==0) {
			return;
		}
		dataBase.deleteMethod(_id);
		myUpDateShow();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			//关闭数据库，退出程序
			dataBase.close();
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	


}
