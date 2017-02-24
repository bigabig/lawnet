package webapp.graph;

import java.util.List;

/**
 * Created by tim on 23.02.2017.
 */
public class Draw {

    public Draw(List<Node> nodes, List<Link> links) {
        this.nodes = nodes;
        this.links = links;
    }

    private List<Node> nodes;
    private List<Link> links;

}
