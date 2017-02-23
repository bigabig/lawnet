package webapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tim on 23.02.2017.
 */
@Entity
@Table(name = "zitate")
public class Zitat {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String aktenzeichen1;

     @NotNull
    private String aktenzeichen2;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Zitat() { }

    public Zitat(long id) {
        this.id = id;
    }

    public Zitat(String aktenzeichen1, String aktenzeichen2) {
        this.aktenzeichen1 = aktenzeichen1;
        this.aktenzeichen2 = aktenzeichen2;
    }

    // Getter and setter methods

    public String getAktenzeichen1() {
        return aktenzeichen1;
    }

    public void setAktenzeichen1(String value) {
        this.aktenzeichen1 = value;
    }

    public String getAktenzeichen2() {
        return aktenzeichen2;
    }

    public void setAktenzeichen2(String value) {
        this.aktenzeichen2 = value;
    }

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

} // class User