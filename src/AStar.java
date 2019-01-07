import mayflower.*;
import java.util.*;

public class AStar extends World {
    int size = 16;
    ArrayList<Node> openSet;
    ArrayList<Node> closedSet;
    Node[][] grid;
    Node current;
    Node end;
    int count = 0;
    boolean searching;
    public AStar()
    {
        Mayflower.showFPS(true);

        setBackground("img/black.png");
        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
        grid = new Node[size][size];
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[0].length; j++)
            {
                grid[i][j] = new Node(i,j);
                if(Math.random() < .25 && !(i == 0 && j == 0) && !(i == size - 1 && j == size - 1))
                {
                    grid[i][j].t = Tag.W;
                }
                addObject(grid[i][j],i * 25,j * 25);
            }
        }
        grid[0][0].setImage("img/green.png");
        grid[0][0].t = Tag.S;
        end = grid[size - 1][size - 1];
        end.setImage("img/red.png");
        end.t = Tag.E;
        current = grid[0][0];
        current.f = h(current);
        openSet.add(current);
        searching = true;
    }

    public void act()
    {
        if(count > 0)
        {
            if(!openSet.isEmpty() && searching)
            {
                current = findLowestF();
                if(current.i == end.i && current.j == end.j)
                {
                    searching = false;
//                    return;
                }
                closedSet.add(current);
                openSet.remove(current);

                ArrayList<Node> neighbors = getNeighbors(current);
                for(int i = 0; i < neighbors.size(); i++)
                {
                    Node n = neighbors.get(i);
                    if(!closedSet.contains(n) && n.t != Tag.W)
                    {
                        double tg = current.g + 1;
                        if(openSet.contains(n))
                        {
                            if(tg < n.g)
                            {
                                n.g = tg;
                            }
                        }
                        else
                        {
                            n.g = tg;
                            openSet.add(n);
                        }
                        n.parent = current;
                        n.h = h(n);
                        n.f = n.g + n.h;
                    }

                }
            }

            for(int i = 0; i < grid.length; i++)
            {
                for(int j = 0; j < grid[0].length; j++)
                {
                    Node n = grid[i][j];
                    if(openSet.contains(n))
                    {
                        n.setImage("img/green.png");
                    }
                    else if(closedSet.contains(n))
                    {
                        n.setImage("img/red.png");
                    }
                    else if(n.t == Tag.W)
                    {
                        n.setImage("img/white.png");
                    }
                    else
                    {
                        n.setImage("img/empty.png");
                    }
                }
            }

            Node temp = current;
            temp.setImage("img/blue.png");
            while(temp.parent != null)
            {
                temp = temp.parent;
                temp.setImage("img/blue.png");
            }

            count = 0;
        }

        count++;

    }

    public Node findLowestF()
    {
        Node min = openSet.get(0);
        for(int i = 1; i < openSet.size(); i++)
        {
            if(openSet.get(i).f < min.f)
            {
                min = openSet.get(i);
            }
        }

        return min;
    }

    public ArrayList<Node> getNeighbors(Node n)
    {
        ArrayList<Node> neighbors = new ArrayList<>();
        Node n1 = tryNode(n.i - 1, n.j);
        Node n2 = tryNode(n.i + 1, n.j);
        Node n3 = tryNode(n.i, n.j - 1);
        Node n4 = tryNode(n.i, n.j + 1);
        if(n1 != null)
        {
            neighbors.add(n1);
        }
        if(n2 != null)
        {
            neighbors.add(n2);
        }
        if(n3 != null)
        {
            neighbors.add(n3);
        }
        if(n4 != null)
        {
            neighbors.add(n4);
        }

        return neighbors;
    }

    public Node tryNode(int i, int j)
    {
        try {
            return grid[i][j];
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public double h(Node n)
    {
        return Math.abs(n.i - end.i) + Math.abs(n.j - end.j);
//        return Math.sqrt(Math.pow(n.i + end.i,2) + Math.pow(n.j + end.j , 2));
    }
}
