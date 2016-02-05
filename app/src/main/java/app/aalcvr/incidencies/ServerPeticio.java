package app.aalcvr.incidencies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alberto on 1/10/2016.
 */
public class ServerPeticio {
    Context context;
    //ProgressDialog progressDialog;

    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String ADRECA_LOGIN = "http://192.168.0.157/index/php/android/login_android.php";
    public static final String ADRECA_REGISTRE = "http://192.168.0.157/index/php/android/registre_android.php";
    public static final String ADRECA_RECUPERACONTRASENYA = "http://192.168.0.157/index/php/android/registre_android.php";


    public ServerPeticio (Context context)
    {
/*        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processant informació...");
        progressDialog.setMessage("Conectant amb el servidor");
        progressDialog.setCancelable(true);*/
        this.context = context;
    }

    public void registreUsuari(Usuari usuari)
    {
        new RegistreUsuari(usuari).execute();
    }
    public void obtenirDadesUsuari(Usuari usuari, ObtenirUsuari llamarUsuari)
    {
        new ObtenirDadesUsuari(usuari, llamarUsuari).execute();
    }

    public void recupearContrasenya(Usuari usuari)
    {
        new RecuperarContrasenya(usuari).execute();
    }

    public class RegistreUsuari extends AsyncTask<Void, Void, Void>
    {
        Usuari usuari;
        public RegistreUsuari(Usuari usuari)
        {
            this.usuari = usuari;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Map<String, String> dadesAEnviar = new HashMap<>();
            dadesAEnviar.put("dni", usuari.getDni());
            dadesAEnviar.put("nom", usuari.getNom());
            dadesAEnviar.put("email", usuari.getEmail());
            dadesAEnviar.put("contrasenya", usuari.getContrasenya());

            BufferedReader reader = null;
            //codificamos los datos antes enviarlos
            String dadesCodificats;
            dadesCodificats = obtenirDadesCodificades(dadesAEnviar);
            try {
                URL url = new URL(ADRECA_REGISTRE);
                HttpURLConnection conexio = (HttpURLConnection) url.openConnection();
                // Metodo POST para activar lo que estamos escribiendo
                conexio.setRequestMethod("POST");
                // después de esto podremos escribir en el body lo que tenemos en dataAenviar
                conexio.setDoOutput(true);
                //conexio.setDoInput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conexio.getOutputStream());
                // escribiendo les dades codificats a OutputStreamWriter
                writer.write(dadesCodificats);
                // enviando los datos al servidor
                writer.flush();
                // reader - Leerá los datos que vienen del servidor linea a linea
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(conexio.getInputStream()));
                String linia;
                while ((linia = reader.readLine()) != null)
                {
                    if (linia.equals("correcte"))
                    {
                        Intent novaActivitat = new Intent(context, LoginActivity.class);
                        context.startActivity(novaActivitat);
                    }
                    else
                    {
                        System.out.println("El usuari o contrasenya es incorrecte");
                    }
                    sb.append(linia);
                }
                linia = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    //Comprovarà les dades introduides
    public class ObtenirDadesUsuari extends AsyncTask<Void, Void, Usuari>
    {
        Usuari usuari;
        ObtenirUsuari userCallBack;
        public ObtenirDadesUsuari(Usuari usuari, ObtenirUsuari llamarUsuari)
        {
            this.usuari = usuari;
            this.userCallBack = llamarUsuari;
        }
        @Override
        protected Usuari doInBackground(Void... params) {
            Map<String, String> dadesAEnviar = new HashMap<String, String>();
            dadesAEnviar.put("email", usuari.getEmail());
            dadesAEnviar.put("contrasenya", usuari.getContrasenya());
            BufferedReader reader = null;
            //codificamos los datos antes enviarlos
            String dadesCodificats;
            dadesCodificats = obtenirDadesCodificades(dadesAEnviar);
            try {
                URL url = new URL(ADRECA_LOGIN);
                HttpURLConnection conexio = (HttpURLConnection) url.openConnection();
                // Metodo POST para activar lo que estamos escribiendo
                conexio.setRequestMethod("POST");
                // después de esto podremos escribir en el body lo que tenemos en dataAenviar
                conexio.setDoOutput(true);
                //conexio.setDoInput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conexio.getOutputStream());
                // escribiendo les dades codificats a OutputStreamWriter
                writer.write(dadesCodificats);
                // enviando los datos al servidor
                writer.flush();
                // reader - Leerá los datos que vienen del servidor linea a linea
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(conexio.getInputStream()));
                String linia;
                while ((linia = reader.readLine()) != null)
                {
                    if (linia.equals("correcte"))
                    {
                        Intent navigationDrawer = new Intent(context, NavigationDrawerActivity.class);
                        context.startActivity(navigationDrawer);
                    }
                    else
                    {
                        /*Toast toast = Toast.makeText(context, "El usuari o contrasenya es incorrecte", Toast.LENGTH_SHORT);
                        toast.show();*/
                    }
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();

            }finally {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    public class RecuperarContrasenya extends AsyncTask<Void, Void, Usuari>{
        Usuari usuari;
        public RecuperarContrasenya(Usuari usuari)
        {
            this.usuari = usuari;
        }
        @Override
        protected Usuari doInBackground(Void... params) {
            Map<String, String> dadesAEnviar = new HashMap<String, String>();
            dadesAEnviar.put("email", usuari.getEmail());
            BufferedReader reader = null;
            //codificamos los datos antes enviarlos
            String dadesCodificats;
            dadesCodificats = obtenirDadesCodificades(dadesAEnviar);
            try {
                URL url = new URL(ADRECA_RECUPERACONTRASENYA);
                HttpURLConnection conexio = (HttpURLConnection) url.openConnection();
                // Metodo POST para activar lo que estamos escribiendo
                conexio.setRequestMethod("POST");
                // después de esto podremos escribir en el body lo que tenemos en dataAenviar
                conexio.setDoOutput(true);
                //conexio.setDoInput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conexio.getOutputStream());
                // escribiendo les dades codificats a OutputStreamWriter
                writer.write(dadesCodificats);
                // enviando los datos al servidor
                writer.flush();
                // reader - Leerá los datos que vienen del servidor linea a linea
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(conexio.getInputStream()));
                String linia;
                while ((linia = reader.readLine()) != null)
                {
                    sb.append(linia + "\n");
                    Intent novaActividad = new Intent(context, RecuperarContrasenya.class);
                    context.startActivity(novaActividad);
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }finally {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    private String obtenirDadesCodificades(Map<String, String> data)
    {
        StringBuilder sb = new StringBuilder();

        for (String key : data.keySet())
        {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key), "UTF-8");

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            if (sb.length() > 0)
            {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

}
