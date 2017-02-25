package webapp.graph;

/**
 * Created by tim on 23.02.2017.
 */
public class Node {

    public Node(String id, int group)
    {
        this.id = id;
        this.group = group;
    }

    private String id;
    private int group;

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Node))return false;
        Node otherNode = (Node) other;
        return (this.id == otherNode.id);
    }

}
