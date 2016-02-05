package app.aalcvr.incidencies;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alberto on 1/25/2016.
 */
public class DadesLocals {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public DadesLocals(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(Usuari usuari) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("nom", usuari.getNom());
        userLocalDatabaseEditor.putString("email", usuari.getEmail());
        userLocalDatabaseEditor.putString("contrasenya", usuari.getContrasenya());
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public Usuari getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }
        String nom = userLocalDatabase.getString("nom", "");
        String email = userLocalDatabase.getString("email", "");
        String contrasenya = userLocalDatabase.getString("contrasenya", "");
        Usuari usuari = new Usuari(email, contrasenya);
        return usuari;
    }
}
