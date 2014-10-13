package com.iluminatic.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.util.Log;
import com.google.gson.Gson;
import com.iluminatic.modelo.Configuracion;
import com.iluminatic.modelo.RespuestaRest;

public class RESTClient {

	public String enviarDatosWeb(String myurl, Configuracion config) throws IOException {
		
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
		DefaultHttpClient http = new DefaultHttpClient(httpParams);

		HttpPost post = new HttpPost(
				"http://192.168.1.39:8080/EjemplosRest/rest/datamanager/recepcion/");

		Gson g = new Gson();
		
		if ( config.getNotificacion50().startsWith("S") ||  config.getNotificacion50().startsWith("s") ){
			
			config.setNotificacion50("si");
		}
		
		
		String envioGson = g.toJson(config);
		
		

		RespuestaRest restRpta = new RespuestaRest();

		try {

			StringEntity objetoJson = new StringEntity(envioGson);
			objetoJson.setContentType("application/json");
			
			post.setEntity(objetoJson);

			HttpResponse response = http.execute(post);

			if (response.getStatusLine().getStatusCode() != 200) {
				// error en respuesta http rest
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			
			String mensajeRecibido = "";

			StringBuilder builder = new StringBuilder();

			while ((mensajeRecibido = reader.readLine()) != null) {
			
				builder.append(mensajeRecibido);

			}

			Gson j = new Gson();
			
			restRpta = j.fromJson(builder.toString(), RespuestaRest.class);

			http.getConnectionManager().shutdown();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.i("RESTCLIENT", "enviarDatosWeb start: " + e.getMessage());
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.i("RESTCLIENT", "enviarDatosWeb start: " + e.getMessage());

			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			restRpta.setMensaje("ERROR REST");
			Log.i("RESTCLIENT", "enviarDatosWeb start: " + e.getMessage());
			
			
		}
		
		return restRpta.getMensaje();
	}

}
