package la.devacademy.pokedex;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] arrayPokemon = new String[]{"Pikachu", "Charmander", "Bulbasaur", "Squirtle", "Caterpie"};
		ArrayList<String> arrayListPokemon = new ArrayList<String>(Arrays.asList(arrayPokemon));
		ArrayAdapter<String> adapterPokemon = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListPokemon);
		ListView listaPokemon = (ListView) findViewById(R.id.listaPokemon);
		listaPokemon.setAdapter(adapterPokemon);
		OnClick onClick = new OnClick();
		listaPokemon.setOnItemClickListener(onClick);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public class OnClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String pokemon = adapter.getItemAtPosition(position).toString();
			Intent intent = new Intent(getApplicationContext(), DetallePokemonActivity.class);
			intent.putExtra(DetallePokemonActivity.POKEMON, pokemon);
			startActivity(intent);
		}
    	
    	
    } 
}
