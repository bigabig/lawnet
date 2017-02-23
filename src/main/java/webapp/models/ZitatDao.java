package webapp.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tim on 23.02.2017.
 */
@Transactional
public interface ZitatDao extends CrudRepository<Zitat, Long> {

    /**
     * Gibt die Metadaten passend zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen1 das aktenzeichen einer Entscheidung.
     */
    public List<Zitat> findByAktenzeichen1(String aktenzeichen1);

    /**
     * Gibt die Metadaten passend zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen2 das aktenzeichen einer Entscheidung.
     */
    public List<Zitat> findByAktenzeichen2(String aktenzeichen2);

    public Zitat findByAktenzeichen1AndAktenzeichen2(String aktenzeichen1, String aktenzeichen2);


} // class UserDao
