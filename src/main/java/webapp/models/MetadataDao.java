package webapp.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tim on 23.02.2017.
 */
@Transactional
public interface MetadataDao extends CrudRepository<Metadata, String> {

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM dokumente d, metadaten m1, zitate z, metadaten m2 WHERE " +
            "d.dateiname = :documentName AND " +
            "d.aktenzeichen = m1.aktenzeichen AND " +
            "m1.aktenzeichen = z.aktenzeichen1 AND " +
            "z.aktenzeichen2 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> documentQuery(@Param("documentName") String documentName);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.aktenzeichen LIKE (CONCAT(:referenceNumber, '%')) AND " +
            "m1.aktenzeichen = z.aktenzeichen1 AND " +
            "z.aktenzeichen2 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> activeAktQuery(@Param("referenceNumber") String referenceNumber);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.aktenzeichen LIKE (CONCAT(:referenceNumber, '%')) AND " +
            "m1.aktenzeichen = z.aktenzeichen2 AND " +
            "z.aktenzeichen1 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> passivAktQuery(@Param("referenceNumber") String referenceNumber);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.datum = :date AND " +
            "m1.typ = :type AND " +
            "m1.aktenzeichen = z.aktenzeichen1 AND " +
            "z.aktenzeichen2 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> activeDatTypQuery(@Param("date") String date, @Param("type") String type);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.datum = :date AND " +
            "m1.typ = :type AND " +
            "m1.aktenzeichen = z.aktenzeichen2 AND " +
            "z.aktenzeichen1 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> passivDatTypQuery(@Param("date") String date, @Param("type") String type);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.typ = :type AND " +
            "m1.aktenzeichen = z.aktenzeichen1 AND " +
            "z.aktenzeichen2 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> activeTypQuery(@Param("type") String type);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.typ = :type AND " +
            "m1.aktenzeichen = z.aktenzeichen2 AND " +
            "z.aktenzeichen1 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> passivTypQuery(@Param("type") String type);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.datum = :date AND " +
            "m1.aktenzeichen = z.aktenzeichen1 AND " +
            "z.aktenzeichen2 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> activeDatQuery(@Param("date") String date);

    @Query(value = "SELECT m1.aktenzeichen as akt1, m1.datum as dat1, m1.typ as typ1, m2.aktenzeichen as akt2, m2.datum as dat2, m2.typ as typ2 FROM metadaten m1, zitate z, metadaten m2 WHERE " +
            "m1.datum = :date AND " +
            "m1.aktenzeichen = z.aktenzeichen2 AND " +
            "z.aktenzeichen1 = m2.aktenzeichen",
            nativeQuery = true)
    public List<Object[]> passivDatQuery(@Param("date") String date);

    /**
     * Gibt die Metadaten passend zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen das aktenzeichen einer Entscheidung.
     */
    public Metadata findByAktenzeichen(String aktenzeichen);

    /**
     * Gibt die Metadaten passend zum Aktenzeichen einer Entscheidung zurück.
     *
     * @param aktenzeichen das aktenzeichen einer Entscheidung.
     */
    public List<Metadata> findByAktenzeichenStartingWith(String aktenzeichen);

    /**
     * Gibt alle Metadaten passend zum Datum einer Entscheidung zurück.
     *
     * @param datum das Datum einer Entscheidung.
     */
    public List<Metadata> findByDatum(String datum);

    /**
     * Gibt alle Metadaten passend zum Typ einer Entscheidung zurück.
     *
     * @param typ der Typ einer Entscheidung.
     */
    public List<Metadata> findByTyp(String typ);

    /**
     * Gibt alle Metadaten passend zum Datum und Typ einer Entscheidung zurück.
     *
     * @param typ der Typ einer Entscheidung.
     */
    public List<Metadata> findByDatumAndTyp(String datum, String typ);

} // class UserDao
