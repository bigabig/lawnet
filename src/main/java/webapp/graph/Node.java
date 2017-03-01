package webapp.graph;

/**
 * Created by tim on 23.02.2017.
 */
public class Node {

    private String id;
    private int group;
    private int size;

    public Node(String id) {
        this.id = id;
        this.group = 0;
        this.size = 5;
    }

    public Node(String id, int group)
    {
        this.id = id;
        this.group = group;
        this.size = 5;
    }

    public Node(String id, int group, int size)
    {
        this.id = id;
        this.group = group;
        this.size = size;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Node))return false;
        Node otherNode = (Node) other;
        return (this.id == otherNode.id);
    }

    public void setGroup(int value) { this.group = value; }

}
