package com.iluminatic.datamanager;

import java.util.Calendar;

import com.iluminatic.modelo.Calculos;
import com.iluminatic.modelo.Configuracion;
import com.iluminatic.monitor.TrafficSnapshot;
import com.iluminatic.sql.BDDataBaseHelper;
import com.iluminatic.sql.BDOpenHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class DataManagerActivity extends ActionBarActivity {

	private BDDataBaseHelper dbHelper;
	private static final String DATABASE_NAME = "BD.DATACHECK";
	private TrafficSnapshot ultimo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_manager);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();

			
			//deleteDatabase(DATABASE_NAME);
			
			/*dbHelper = new BDDataBaseHelper(this);

			Configuracion config = dbHelper.recuperarRegistro();

			if (null == config.getId()) {

				dbHelper.insertarRegistroConfig();
				Intent intent = new Intent(this, ConfigActivity.class);
				this.startActivity(intent);
			}*/
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_config:
			Intent intent = new Intent(this, ConfigActivity.class);
			this.startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private BDDataBaseHelper dbHelper;
		private BDOpenHelper openHelper;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_data_manager,
					container, false);

			TrafficSnapshot ultimo = new TrafficSnapshot(container.getContext());

			TrafficSnapshot previo = new TrafficSnapshot(container.getContext());

			Calendar c1 = Calendar.getInstance();

			String dia = Integer.toString(c1.get(Calendar.DATE));

			TextView txtConsumo = (TextView) rootView
					.findViewById(R.id.txtConsumo);

			Calculos calc = new Calculos();

			dbHelper = new BDDataBaseHelper(container.getContext());

			Configuracion config = dbHelper.recuperarRegistro();

			if ( config.getId() != null && !config.getPlanMb().equals("0") ) {

				txtConsumo.setText(String.valueOf(calc.calulcarPorcentaje(
						calc.convertirBytesToMegas(ultimo.device.rx),
						config.getPlanMb())));

				TextView txtCantidadRecibida = (TextView) rootView
						.findViewById(R.id.txtCantidadRecibida);

				txtCantidadRecibida.setText(String.valueOf(calc
						.convertirBytesToMegas(ultimo.device.rx)));
				
				TextView txtEnviados = (TextView) rootView
						.findViewById(R.id.txtEnviados);
				
				txtEnviados.setText(String.valueOf(calc
						.convertirBytesToMegas(ultimo.device.tx)));
				

			} else {
				// deleteDatabase(DATABASE_NAME);
				//txtConsumo.setText("0"); debe llamar a la actividad de configuracion
				
				dbHelper.insertarRegistroConfig();
				Intent intent = new Intent(container.getContext(), ConfigActivity.class);
				this.startActivity(intent);

			}
			return rootView;
		}
	}
}