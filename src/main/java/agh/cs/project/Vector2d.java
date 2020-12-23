package agh.cs.project;

import java.util.Objects;

public class Vector2d
{
    public final int x;
    public final int y;

    public Vector2d(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "(" + x +"," + y +')';

    }

    public boolean precedes(Vector2d other)
    {
        return this.x<=other.x && this.y<=other.y;//I know, that "this." isn't necessary, but I think it improves code readability
    }

    public boolean follows(Vector2d other)
    {
        return this.x>=other.x && this.y>=other.y;
    }

    public Vector2d upperRight(Vector2d other)
    {
        return new Vector2d(Math.max(this.x,other.x),Math.max(this.y,other.y));//I use the max function to find the biggest x and y
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        return new Vector2d(Math.min(this.x,other.x),Math.min(this.y,other.y));//I use the min function to find lowest x and y
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x+other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x-other.x,this.y-other.y);
    }

    @Override
    public boolean equals(Object o) {//I generated this method using built-in tools
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {//I generated this method using built-in tools
        return Objects.hash(x, y);
    }

    public Vector2d opposite()
    {
        return new Vector2d((-1)*x,(-1)*y);
    }

}
