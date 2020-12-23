package agh.cs.project;

import java.util.Random;

public enum MapDirection
{
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHWEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST;


    public String toString() {
        return switch(this)
                {
                    case EAST-> "Wschód";
                    case WEST-> "Zachód";
                    case NORTH-> "Północ";
                    case SOUTH ->  "Południe";
                    case NORTHWEST -> "Północny Zachód";
                    case NORTHEAST -> "Północny Wschód";
                    case SOUTHEAST -> "Południowy Wschód";
                    case SOUTHWEST -> "Południowy Zachód";
                };
    }


    public MapDirection next()
    {
        return switch(this)
        {
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST ->NORTHWEST;
            case NORTHWEST -> NORTH;
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST ->SOUTHEAST;
            case SOUTHEAST -> SOUTH;
        };
    }

    public MapDirection previous()
    {
        return switch(this) {
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> EAST;
            case EAST->NORTHEAST;
            case NORTHEAST -> NORTH;
            case NORTH-> NORTHWEST;
            case NORTHWEST -> WEST;
            case WEST ->SOUTHWEST;
            case SOUTHWEST ->SOUTH;
        };
    }

    public Vector2d toUnitVector()
    {
        return switch(this)
        {
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case SOUTHEAST -> new Vector2d(1,-1);
            case NORTHEAST -> new Vector2d(1,1);
            case NORTHWEST -> new Vector2d(-1,1);
            case SOUTHWEST -> new Vector2d(-1,-1);
        };
    }

    public static MapDirection random()
    {
        Random r = new Random();
        return switch (r.nextInt(8))
        {
            case 0 ->NORTH;
            case 1->SOUTH;
            case 2->EAST;
            case 3-> WEST;
            case 4->SOUTHWEST;
            case 5->NORTHEAST;
            case 6->NORTHWEST;
            case 7 -> SOUTHEAST;
            default -> NORTH;
        };
    }
}
