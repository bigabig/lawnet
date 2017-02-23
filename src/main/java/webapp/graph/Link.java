package webapp.graph;

/**
 * Created by tim on 23.02.2017.
 */
public class Link {

    private String source;
    private String target;
    private int value;

    public Link(String source, String target, int value)
    {
        this.source = source;
        this.target = target;
        this.value = value;
    }
}
