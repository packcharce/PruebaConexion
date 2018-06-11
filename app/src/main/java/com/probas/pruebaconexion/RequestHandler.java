/*
 * Copyright (c) Carlos Solana. Todos los derechos reservados.
 */

package com.probas.pruebaconexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    //Metodo que hace la peticion http
    //Primer argumento: url donde hacer la peticion
    //Segundo argumento: hashmap con las parejas de valores para el PHP
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {
        URL url;

        //StringBuilder para guardar la respuesta del servidor
        StringBuilder sb = new StringBuilder();
        try {
            //Inicializando la Url
            url = new URL(requestURL);

            //Conexion http
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Propiedades del servidor
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;

                //Respuesta del servidor
                while ((response = br.readLine()) != null) {
                    System.out.println(response);
                    sb.append(response);
                }
            }
        }catch (SocketTimeoutException ste){
            throw new RuntimeException();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Metodo que crea el mensaje para el servidor
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
