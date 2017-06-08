package authentification.modele;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Utilisateur {

    public Utilisateur(String email) {
        id = Hashing.murmur3_32().hashString(email, Charsets.UTF_8).toString();
    }

    public String id() {
        return id;
    }

    private final String id;
}
