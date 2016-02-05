package app.aalcvr.incidencies;

/**
 * Created by Alberto on 1/24/2016.
 */
public class Usuari {
    private String dni;
    private String nom;
    private String email;
    private String contrasenya;

    // Constructor per al registre
    public Usuari (String dni, String nom, String email, String contrasenya)
    {
        this.dni = dni;
        this.nom = nom;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    // Constructor per al login
    public Usuari(String email, String contrasenya)
    {
        this.email = email;
        this.contrasenya = contrasenya;
    }

    public Usuari(String email)
    {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenya() {
        return contrasenya;
    }
}
