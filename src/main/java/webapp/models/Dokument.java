package webapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tim on 26.02.2017.
 */
@Entity
@Table(name = "dokumente")
public class Dokument {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // Das Aktenzeichen von einem Beschluss
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String aktenzeichen;

    // Der Volltext eines Beschluss
    @NotNull
    private String text;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Dokument() { }

    public Dokument(String text) {
        this.text = text;
    }

    public Dokument(String aktenzeichen, String text) {
        this.aktenzeichen = aktenzeichen;
        this.text = text;
    }

    // Getter and setter methods

    public String getAktenzeichen() {
        return aktenzeichen;
    }

    public void setAktenzeichen(String value) {
        this.aktenzeichen = value;
    }

    public String getText() { return text; }

    public void setText(String value) {
        this.text = value;
    }

} //class Dokument
