package com.iluminatic.sql;


import com.iluminatic.modelo.Configuracion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class BDDataBaseHelper {

	private static final String DATABASE_NAME = "BD.DATACHECK";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_TABLE_NAME = "CONFIG";
	
	private SQLiteDatabase dataBase;
	private BDOpenHelper bdOpenHelper;
	

	public BDDataBaseHelper(Context context) {
		
		bdOpenHelper = new BDOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		//bdOpenHelper.onCreate(DATABASE_NAME);
		dataBase = bdOpenHelper.getWritableDatabase();

	}
	
	public void insertarRegistroConfig(){
		
		ContentValues newRow = new ContentValues();
		// hard-coded for simplicity
		newRow.put("_id", "1");
		newRow.put("plan_mb", "0");
		newRow.put("dia_inicio", "0");
		newRow.put("uso_mb", "0");
		newRow.put("noti_50", "OFF");
		
		dataBase.insert(DATABASE_TABLE_NAME, null, newRow);
		//dataBase.close();
		
	}
	
	public Configuracion recuperarRegistro() {

		
		// Select columns to retrieve in the form of String array
		String[] resultColumns = new String[] { "_id","plan_mb", "dia_inicio",
				"noti_50", "uso_mb" };

		Cursor cursor = dataBase.query(DATABASE_TABLE_NAME, resultColumns, null,
				null, null, null, null, null);

		// String res = "All capital cities: ";

		Configuracion config = new Configuracion();

		Integer cinde = cursor.getColumnIndex("_id");
		Integer cindex = cursor.getColumnIndex("plan_mb");
		Integer cindex2 = cursor.getColumnIndex("dia_inicio");
		Integer cindex3 = cursor.getColumnIndex("noti_50");
		Integer cindex4 = cursor.getColumnIndex("uso_mb");

		if (cursor.moveToFirst()) {

			do {
				// res += cursor.getString(cindex) + ", ";
				config.setId(cursor.getString(cinde));
				config.setPlanMb(cursor.getString(cindex));
				config.setUsoMB(cursor.getString(cindex4));
				config.setDiaInicioFacturacion(cursor.getString(cindex2));
				config.setNotificacion50(cursor.getString(cindex3));

			} while (cursor.moveToNext());
		}

		//dataBase.close();

		return config;

	}
	
	
	
	public void actualizarConfiguracion(Configuracion config) {

		

		// Create a new row and insert it into the database.
		ContentValues newValues = new ContentValues();
		// hard-coded for simplicity
		newValues.put("plan_mb", config.getPlanMb());
		newValues.put("dia_inicio", config.getDiaInicioFacturacion());
		newValues.put("noti_50", config.getNotificacion50());
		newValues.put("uso_mb", config.getUsoMB());

		/*newValues.put("dia_inicio", "");
		newValues.put("noti_50", "");
		newValues.put("uso_mb", "");*/
		// Create whereClause
		String whereClause = "_id=?";
		String[] whereArgs = new String[] { "1" };

		dataBase.update(DATABASE_TABLE_NAME, newValues, whereClause, whereArgs);

		//dataBase.close();
	

	}
	
	public void eliminarTabla(){
		
		bdOpenHelper.onUpgrade(dataBase, 1,2);
		
	}

}
