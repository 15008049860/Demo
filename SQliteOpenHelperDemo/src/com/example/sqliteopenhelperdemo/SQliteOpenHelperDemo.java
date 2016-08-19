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
	private int _id;//��ǰ�α�Cursor���ڵ��ֶ�ֵ

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_open_helper_demo);

		mEditText = (EditText) this.findViewById(R.id.edit_id);
		mListView = (ListView) this.findViewById(R.id.listView_id);
		mListView.setOnItemClickListener(clickListener);
		mListView.setOnItemSelectedListener(selectedListener);
		
		//����SQLiteOpenHelper���������
		dataBase=new MyDataBase(this);
	    /* ȡ��DataBase������� */
		mCursor=dataBase.query();
	    /* new SimpleCursorAdapter����myCursor���룬��ʾ���ݵ��ֶ�Ϊ   _id��todo_text */ 
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
   // ListView�ĵ���¼�����
	OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			mCursor.moveToPosition(arg2);
			_id=mCursor.getInt(mCursor.getColumnIndex(MyDataBase.FIELD_id));
			mEditText.setText(mCursor.getString(mCursor.getColumnIndex(MyDataBase.FIELD_TEXT)));
		}

	};
	//�������������ֵ��¼�����
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

	//����ListView
	public void  myUpDateShow()
	{
		         /* ���²�ѯ */
				mCursor.requery();
				//��֪�������Ѿ��ı�
				adapter.notifyDataSetChanged();
				mEditText.setText("");
				_id = 0; 
	}
	//����
	public void add()
	{
		String str=mEditText.getText().toString();
		if (str.equalsIgnoreCase("")) {
			return;
		}
		 /* �������ݵ����ݿ� */
		dataBase.addMethod(str);
		myUpDateShow();
	}
	//�޸�
	public void mod()
	{
		String str=mEditText.getText().toString();
		if (str.equalsIgnoreCase("")) {
			return;
		}
		//�޸�
		dataBase.modMethod(_id,str);
		myUpDateShow();
	}
	//ɾ��
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
			//�ر����ݿ⣬�˳�����
			dataBase.close();
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	


}
