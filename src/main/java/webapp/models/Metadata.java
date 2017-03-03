package webapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * An entity User composed by three fields (id, email, name).
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 *
 * @author webapp
 */
@Entity
@Table(name = "metadaten")
public class Metadata {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // Das Aktenzeichen von einem Beschluss
    @Id
    private String aktenzeichen;

    // Das Datum an von einem Beschluss
    private String datum;

    // Der Typ eines Beschluss
    @NotNull
    private String typ;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Metadata() { }

    public Metadata(String aktenzeichen) {
        this.aktenzeichen = aktenzeichen;
    }

    public Metadata(String datum, String typ) {
        this.datum = datum;
        this.typ = typ;
    }

    public Metadata(String aktenzeichen, String datum, String typ) {
        this.aktenzeichen = aktenzeichen;
        this.datum = datum;
        this.typ = typ;
    }

    // Getter and setter methods

    public String getAktenzeichen() {
        return aktenzeichen;
    }

    public void setAktenzeichen(String value) {
        this.aktenzeichen = value;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String value) {
        this.datum = value;
    }

    public String getTyp() { return typ; }

    public void setTyp(String value) {
        this.typ = value;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Metadata)) return false;
        Metadata otherMetadata = (Metadata) other;
        return (this.aktenzeichen.equals(otherMetadata.getAktenzeichen()));
    }

} // class Metadata
