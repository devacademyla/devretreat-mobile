package com.devacademyla.pokedev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.devacademyla.pokedev.lista.PokemonAdapter;
import com.devacademyla.pokedev.models.Pokemon;
import com.devacademyla.pokedev.utils.PokedexUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.lista_pokemon);
        new ListaPokemonTask().execute();
        
    }
    
    private void updateListView(ArrayList<Pokemon> pokemons){
    	listView.setFastScrollEnabled(true);
		listView.setAdapter(new PokemonAdapter(this, R.layout.pokemon, pokemons));
	}
    
    private class ListaPokemonTask extends AsyncTask<Object, Void, ArrayList<Pokemon>>{
    	
    	private ProgressDialog progressDialog;
    	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);;
			progressDialog.setMessage(getResources().getString(R.string.cargando_lista_pokemon));
			progressDialog.show();
		}

		@Override
		protected ArrayList<Pokemon> doInBackground(Object... params) {
			ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
			
			try {
				String lista = PokedexUtil.getListaPokemon();
				JSONObject jsonResponseLista = new JSONObject(lista);
				JSONArray jsonArrayPokemon = jsonResponseLista.getJSONArray("pokemon");
				JSONObject jsonObject;
				
				for(int i = 0; i< jsonArrayPokemon.length(); i++){
					jsonObject = (JSONObject) jsonArrayPokemon.get(i);
					Pokemon pokemon = new Pokemon();
					String nombre = jsonObject.getString("name");
					String resource = jsonObject.getString("resource_uri");
					pokemon.setResource(resource);
					pokemon.setNombre(nombre);
					pokemons.add(i, pokemon);
				}
				
				Collections.sort(pokemons, new Comparator<Pokemon>() {
					@Override
					public int compare(Pokemon pokemon1, Pokemon pokemon2) {
						return pokemon1.getNombre().compareTo(pokemon2.getNombre());
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("JSON error: ", Log.getStackTraceString(e));
			}			
			return pokemons;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Pokemon> pokemons) {
			super.onPostExecute(pokemons);
			progressDialog.dismiss();
			if (pokemons.isEmpty()) {
				Toast.makeText(MainActivity.this, getResources().getString(R.string.cargando_lista_pokemon),
						Toast.LENGTH_SHORT).show();
			} else {
				updateListView(pokemons);
				Toast.makeText(MainActivity.this, getResources().getString(R.string.cargando_lista_pokemon),
						Toast.LENGTH_SHORT).show();
			}
		}    	
    }
}
