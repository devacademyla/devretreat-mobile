package com.iluminatic.datamanager;

import java.io.IOException;

import com.iluminatic.modelo.Configuracion;
import com.iluminatic.rest.RESTClient;
import com.iluminatic.sql.BDDataBaseHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Build;

public class ConfigActivity extends ActionBarActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_manager);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private RESTClient restClient = new RESTClient();
		private Toast toast1;
		private static final String DATABASE_NAME = "BD.DATACHECK";

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater,
				final ViewGroup container, Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_config,
					container, false);

			final BDDataBaseHelper dbHelper = new BDDataBaseHelper(
					container.getContext());
			Configuracion config = dbHelper.recuperarRegistro();

			final EditText editPlanMb = (EditText) rootView
					.findViewById(R.id.editPlanMb);
			final EditText editUsoMb = (EditText) rootView
					.findViewById(R.id.editUsoMb);
			final EditText editDiaInicio = (EditText) rootView
					.findViewById(R.id.editDiaInicio);
			final ToggleButton notificacion50 = (ToggleButton) rootView
					.findViewById(R.id.toggleButton1);

			editPlanMb.setText(config.getPlanMb());
			editUsoMb.setText(config.getUsoMB());
			editDiaInicio.setText(config.getDiaInicioFacturacion());
			notificacion50.setText(config.getNotificacion50());
			// config.setNotificacion50(notificacion50.getText().toString());

			Button btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);

			btnGuardar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Configuracion configuracion = new Configuracion();
					configuracion.setPlanMb(editPlanMb.getText().toString());
					configuracion.setUsoMB(editUsoMb.getText().toString());
					configuracion.setDiaInicioFacturacion(editDiaInicio
							.getText().toString());
					configuracion.setNotificacion50(notificacion50.getText()
							.toString());
					dbHelper.actualizarConfiguracion(configuracion);
					Intent intent = new Intent(v.getContext(),
							DataManagerActivity.class);
					v.getContext().startActivity(intent);

				}
			});

			Button btnSincronizar = (Button) rootView
					.findViewById(R.id.btnSincronizar);

			btnSincronizar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Configuracion configuracion = new Configuracion();
					configuracion.setPlanMb(editPlanMb.getText().toString());
					configuracion.setUsoMB(editUsoMb.getText().toString());
					configuracion.setDiaInicioFacturacion(editDiaInicio
							.getText().toString());
					configuracion.setNotificacion50(notificacion50.getText()
							.toString());

					ConnectivityManager connMgr = (ConnectivityManager) getActivity()
							.getSystemService(Context.CONNECTIVITY_SERVICE);

					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

					if (networkInfo != null && networkInfo.isConnected()) {

						ServicioRestTask asyncTask = new ServicioRestTask(
								container.getContext());
						asyncTask.setConfig(configuracion);
						asyncTask.execute("");

					} else {

						toast1 = Toast.makeText(container.getContext(),
								"Conexion no disponible", Toast.LENGTH_SHORT);

						toast1.show();
					}

				}
			});

			Button btnConfigInicial = (Button) rootView
					.findViewById(R.id.btnConfigInicial);

			btnConfigInicial.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					getActivity().deleteDatabase(DATABASE_NAME);

					toast1 = Toast.makeText(container.getContext(),
							"BD Eliminada.", Toast.LENGTH_SHORT);

					toast1.show();

					Intent intent = new Intent(v.getContext(),
							DataManagerActivity.class);
					v.getContext().startActivity(intent);
				}
			});

			return rootView;
		}

		private class ServicioRestTask extends AsyncTask<String, Void, String> {

			private Configuracion config;
			private Context mContext;

			public ServicioRestTask(Context context) {

				mContext = context;
			}

			public Configuracion getConfig() {
				return config;
			}

			public void setConfig(Configuracion config) {
				this.config = config;
			}

			@Override
			protected String doInBackground(String... urls) {

				// params comes from the execute() call: params[0] is the url.
				try {

					return restClient.enviarDatosWeb(urls[0], config);

				} catch (IOException e) {

					return "ERROR CONSUMIENDO SERVICIO";

				}
			}

			// onPostExecute displays the results of the AsyncTask.
			@Override
			protected void onPostExecute(String result) {

				toast1 = Toast.makeText(mContext, result, Toast.LENGTH_SHORT);

				toast1.show();

			}
		}
	}

}
