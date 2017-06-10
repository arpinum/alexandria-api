package authentification.modele;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public final class IdUtilisateur {

    public static String depuisEmail(String email) {
        return Hashing.murmur3_32().hashString(email, Charsets.UTF_8).toString();
    }
}
