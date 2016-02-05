package app.aalcvr.incidencies;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecuperarContrasenyaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEnviarContrasenya;
    EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenya);
        btnEnviarContrasenya = (Button) findViewById(R.id.btnEnviarContrasenya);
        etEmail = (EditText) findViewById(R.id.etEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnEnviarContrasenya:
                Usuari usuari;
                String email;
                email = etEmail.getText().toString();
                usuari = new Usuari(email);
                ServerPeticio serverPeticio = new ServerPeticio(this);
                serverPeticio.recupearContrasenya(usuari);
                break;
        }
    }
}
