import mayflower.*;

public class Node extends Actor {
    int i,j;
    double f,g,h;
    Tag t;
    Node parent;
    boolean changed;
    public Node(int r, int c)
    {
        setImage("img/empty.png");
        i = r;
        j = c;
        t = Tag.N;
        f = 0;
        g = 0;
        h = 0;
        parent = null;
        changed = true;
    }

    public void act()
    {

    }
}

enum Tag {
    S,E,N,W
}