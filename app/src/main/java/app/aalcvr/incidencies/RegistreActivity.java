package app.aalcvr.incidencies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistreActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvPrivacitat, editTextDataNaixement;
    private EditText etDni, etNomUsuari, etEmail, etContrasenya;
    private Button btnCreaCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        tvPrivacitat = (TextView) findViewById(R.id.politica);
        etDni = (EditText) findViewById(R.id.etDni);
        etNomUsuari = (EditText) findViewById(R.id.nomUsuari);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContrasenya = (EditText) findViewById(R.id.etContrasenya);
        btnCreaCompte = (Button) findViewById(R.id.btnCreaCompte);
        btnCreaCompte.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCreaCompte:
                String dni = etDni.getText().toString();
                String nom = etNomUsuari.getText().toString();
                String email = etEmail.getText().toString();
                String contrasenya = etContrasenya.getText().toString();
                String encContrasenya = Encriptacio.getStringMessageDigest(contrasenya, Encriptacio.SHA1);
                Toast toast = Toast.makeText(this, encContrasenya, Toast.LENGTH_SHORT);
                toast.show();
                Usuari usuariRegistrat = new Usuari(dni, nom, email, encContrasenya);
                //Peticio server
                ServerPeticio serverPeticio = new ServerPeticio(RegistreActivity.this);
                serverPeticio.registreUsuari(usuariRegistrat);
        }
    }
    //Comprovacio de camps buits
    public boolean esBuit()
    {
        boolean enc = false;

        return enc;
    }
}
