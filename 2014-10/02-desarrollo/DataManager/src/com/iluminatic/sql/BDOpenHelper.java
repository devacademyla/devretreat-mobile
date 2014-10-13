package com.iluminatic.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class BDOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "BD.DATACHECK";
	
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_TABLE_NAME = "CONFIG";

	private static final String DATABASE_CREATE_TABLE = "create table if not exists "
			+ DATABASE_TABLE_NAME
			+ " (_id text not null primary key , "
			+ " plan_mb text not null, "
			+ " dia_inicio text not null, noti_50 text not null, uso_mb text not null)";

	

	public BDOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL(DATABASE_CREATE_TABLE);

		//db.close();
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE "+ DATABASE_TABLE_NAME);
		
	}
	
	
	
}
