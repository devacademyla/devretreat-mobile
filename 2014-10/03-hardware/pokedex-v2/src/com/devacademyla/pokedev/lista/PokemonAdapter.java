package com.devacademyla.pokedev.lista;

import java.util.ArrayList;

import com.devacademyla.pokedev.R;
import com.devacademyla.pokedev.models.Pokemon;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

	private Context context;
	private ArrayList<Pokemon> pokemons;
	
	public PokemonAdapter(Context context, int resource, ArrayList<Pokemon> pokemons) {
		super(context, resource, pokemons);
		this.context = context;
		this.pokemons = pokemons;
	}
	
	static class ViewHolder{
		public TextView nombre;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.pokemon, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.nombre = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.nombre.setText(pokemons.get(position).getNombre());
		
		return convertView;
	}
}
