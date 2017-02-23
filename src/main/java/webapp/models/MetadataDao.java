package webapp.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tim on 23.02.2017.
 */
@Transactional
public interface MetadataDao extends CrudRepository<Metadata, String> {

    /**
     * Gibt die Metadaten passend zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen das aktenzeichen einer Entscheidung.
     */
    public Metadata findByAktenzeichen(String aktenzeichen);

    /**
     * Gibt alle Metadaten passend zum Datum einer Entscheidung zurück.
     *
     * @param datum das Datum einer Entscheidung.
     */
    public List<Metadata> findByDatum(String datum);

} // class UserDao
