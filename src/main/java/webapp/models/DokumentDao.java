package webapp.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by tim on 26.02.2017.
 */
@Transactional
public interface DokumentDao extends CrudRepository<Dokument, String> {

    /**
     * Gibt das vollständige Dokument (Volltext) zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen das aktenzeichen einer Entscheidung.
     */
    public Dokument findByAktenzeichen(String aktenzeichen);

    /**
     * Gibt das vollständige Dokument (Volltext) zum Dateinamen einer Entscheidung zurück.
     *
     * @param dateiname der Dateiname einer Entscheidung.
     */
    public Dokument findByDateiname(String dateiname);
}
