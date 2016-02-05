package app.aalcvr.incidencies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etUsuari, etContrasenya;
    Button btnRegistre, btnInici;
    TextView tvRecordaContrasenya;
    //UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuari = (EditText) findViewById(R.id.loginUsuari);
        etContrasenya = (EditText) findViewById(R.id.loginContrasenya);
        btnInici = (Button) findViewById(R.id.btnIniciar);
        btnRegistre = (Button) findViewById(R.id.btnRegistre);
        tvRecordaContrasenya = (TextView) findViewById(R.id.tvRecordaContrasenya);
        //
        btnInici.setOnClickListener(this);
        btnRegistre.setOnClickListener(this);
        tvRecordaContrasenya.setOnClickListener(this);

        //userLocalStore = new UserLocalStore(this);
    }

    // En l'escolta de l'opció triada per l'usuari
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnIniciar:
                // Comprovació de camps buits
                if (!esBuit(etUsuari, etContrasenya)){
                    if (estaConnectat()){
                        String email = etUsuari.getText().toString();
                        String contrasenya = etContrasenya.getText().toString();
                        String encriptContrasenya = Encriptacio.getStringMessageDigest(contrasenya, Encriptacio.SHA1);
                        Usuari usuari = new Usuari(email, encriptContrasenya);
                        //Comprovacio de l'usuari
                        if (existeUsuari(usuari))
                        {
                            Toast.makeText(LoginActivity.this, "Iniciant...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.btnRegistre:
                Intent novaActivitat = new Intent(LoginActivity.this, RegistreActivity.class);
                startActivity(novaActivitat);
                break;
            case R.id.tvRecordaContrasenya:
                Intent obrirRecuperaContrasenya = new Intent(LoginActivity.this, RecuperarContrasenyaActivity.class);
                startActivity(obrirRecuperaContrasenya);
                break;
        }
    }

    // Mètodes
    private boolean esBuit(EditText editTextUsuari, EditText editTextContrasenya){

        boolean esBuit = true;
        if (editTextUsuari.getText().toString().equals(""))
        {
            editTextUsuari.requestFocus();
            Toast.makeText(LoginActivity.this, "El camp usuari està buit", Toast.LENGTH_SHORT).show();
        }
        else if (editTextContrasenya.getText().toString().equals(""))
        {
            editTextContrasenya.requestFocus();
            Toast.makeText(LoginActivity.this, "El camp contrasenya està buit", Toast.LENGTH_SHORT).show();
        }
        else
        {
            esBuit = false;
        }
        return esBuit;
    }

    // Comprovació de connexió a internet
    private boolean estaConnectat(){

        boolean connectat = false;
        ConnectivityManager cm = (ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            connectat = true;
        }
        else {
            Toast toast = Toast.makeText(LoginActivity.this, "Comprova la teva connexió i torna a intentar-ho", Toast.LENGTH_SHORT);
            toast.show();
        }
        return connectat;
    }

    private boolean existeUsuari(Usuari usuari)
    {
        boolean enc = false;
        ServerPeticio serverPeticio = new ServerPeticio(LoginActivity.this);
        serverPeticio.obtenirDadesUsuari(usuari, new ObtenirUsuari() {
            @Override
            public void done(Usuari returnedUsuari) {
                if (returnedUsuari == null)
                {
                    mostrarMissatgeError();
                } else {
                    logUserIn(returnedUsuari);
                }
            }
        });
        return enc;
    }

    private void mostrarMissatgeError()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Usuari/Contrasenya no coincideixen");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(Usuari returnedUser)
    {

    }
}
