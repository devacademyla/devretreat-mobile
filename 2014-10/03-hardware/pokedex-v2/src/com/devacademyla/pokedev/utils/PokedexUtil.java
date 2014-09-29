package com.devacademyla.pokedev.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class PokedexUtil {
	private static final String TAG = null;

	public static String getListaPokemon(){
			
			HttpURLConnection httpConnection = null;
			BufferedReader bufferedReader = null;
			StringBuilder response = new StringBuilder();

			try {
				URL url = new URL(ConstantesUtil.URL_LISTA_POKEMON);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

				String line;
				while ((line = bufferedReader.readLine()) != null){            
					response.append(line);	
				}
				
				Log.d(TAG, "GET response code: " + String.valueOf(httpConnection.getResponseCode()));
				Log.d(TAG, "JSON response: " + response.toString());
				return response.toString();

			} catch (Exception e) {
				Log.e(TAG, "GET error: " + Log.getStackTraceString(e));      
				return null;

			} finally {
				if(httpConnection != null){
					httpConnection.disconnect();
				}
			}
		}
	public static String getPokemon(String resource){
		
		HttpURLConnection httpConnection = null;
		BufferedReader bufferedReader = null;
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL(ConstantesUtil.URL + resource);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null){            
				response.append(line);	
			}
			
			Log.d(TAG, "GET response code: " + String.valueOf(httpConnection.getResponseCode()));
			Log.d(TAG, "JSON response: " + response.toString());
			return response.toString();

		} catch (Exception e) {
			Log.e(TAG, "GET error: " + Log.getStackTraceString(e));      
			return null;

		} finally {
			if(httpConnection != null){
				httpConnection.disconnect();
			}
		}
	}
}
