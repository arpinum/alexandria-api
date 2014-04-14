package fr.arpinum.graine.infrastructure.bus;

public class ResultatExecution<TReponse> {

    public static <TReponse> ResultatExecution<TReponse> succes(TReponse reponse) {
        return new ResultatExecution<>(reponse, true);
    }

    public static <TReponse> ResultatExecution<TReponse> erreur(Throwable erreur) {
        return new ResultatExecution<>(erreur);
    }


    private ResultatExecution(TReponse reponse, boolean succes) {
        this.reponse = reponse;
        this.succes = succes;
        erreur = null;
    }

    public ResultatExecution(Throwable erreur) {
        this.erreur = erreur;
        succes = false;
        reponse = null;
    }

    public TReponse donnees() {
        return reponse;
    }

    public boolean isSucces() {
        return succes;
    }

    public boolean isErreur() {
        return !succes;
    }

    public Throwable erreur() {
        return erreur;
    }

    private final TReponse reponse;
    private final boolean succes;
    private final Throwable erreur;
}
