package webapp.models.watson;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tim on 28.02.2017.
 */
@javax.persistence.Entity
@Table(name = "entities")
public class Entity {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int count;

    private String text;

    private String type;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public Entity() { }

    public Entity(long id) {
        this.id = id;
    }

    public Entity(int count, String text, String type) {
        this.count = count;
        this.text = text;
        this.type = type;
    }

    // Getter and setter methods

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

} // class Entity
