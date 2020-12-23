package agh.cs.project;

import java.util.*;

public class WorldMap implements IPositionChangeObserver
{
    private final int width;
    private final int height;
    private final float jungle_ratio;//check if float
    private final float start_energy;
    private final float move_energy;
    private final float plant_energy;

    private final int px;
    private final int py;
    private final int width_jg;
    private final int height_jg;

    public int numOfPlants=0;

    private int chosen=0;




    private final Map<Vector2d,Field> fields = new LinkedHashMap<>();
    private final LinkedList<Animal> animals_alive = new LinkedList<Animal>();
    private final LinkedList<Animal> animals_dead = new LinkedList<Animal>();
    private final List<Vector2d> free_space = new ArrayList<Vector2d>();
    private final List<Vector2d> free_space_jg = new ArrayList<Vector2d>();


    public WorldMap(int width, int height, float jungle_ratio,float start_energy, float move_energy,float plant_energy )
    {
        this.width = width;
        this.height = height;
        this.jungle_ratio = jungle_ratio;
        this.start_energy = start_energy;
        this.move_energy = move_energy;
        this.plant_energy = plant_energy;


        this.width_jg = (int)(jungle_ratio * width);
        this.height_jg = (int) (jungle_ratio * height);
        this.px = (width - width_jg)/2;
        this.py = (height - height_jg)/2;
        for(int x =0;x<=width;x++)
        {
            for(int y=0;y<= height;y++)
            {
                if(x>=px && x<=width - px && y>=py && y<=height-py)
                {
                    free_space_jg.add(new Vector2d(x,y));
                }
                else
                {
                    free_space.add(new Vector2d(x,y));
                }
            }
        }



    }

    public void run()
    {
        for(Animal a : animals_alive)
        {
            a.move(move_energy);
        }




    }

    public void mate()
    {
        Collection<Field> col = fields.values();
        ArrayList<Field> copy = new ArrayList<>(col);
        for(Field f: copy)
        {
            f.mate(start_energy,this);
        }

    }
    public void eat()
    {
        Collection<Field> col = fields.values();
        for(Field f: col)
        {
            if(f.eat(plant_energy))
            {
                numOfPlants-=1;
            }
        }


    }

    public void grow_animals()
    {
        for(Animal a :animals_alive)
        {
            a.grow_older();
        }
    }

    public void cleanDeadBodies()
    {
        LinkedList<Animal> copy =(LinkedList<Animal>) animals_alive.clone();
        for(Animal a :copy)
        {
            if(a.getEnergy()<=0)
            {
                Vector2d pos = a.getPosition();
                animals_alive.remove(a);
                animals_dead.add(a);
                Field f = fields.get(pos);
                f.clean(a);
                if(f.isEmpty())
                {
                    fields.remove(pos);
                }
            }
        }
    }


    public void growNewPlants()
    {
        Random r = new Random();
        if(free_space_jg.size()>1)
        {
            numOfPlants+=1;
            int p = r.nextInt(free_space_jg.size()-1);
            fields.put(free_space_jg.get(p),new Field(free_space_jg.get(p)));
            free_space_jg.remove(p);
        }
        if(free_space.size()>1)
        {
            numOfPlants+=1;
            int p = r.nextInt(free_space.size()-1);
            fields.put(free_space.get(p),new Field(free_space.get(p)));
            free_space.remove(p);
        }


    }

    public Vector2d nextMove(Vector2d position)
    {
        int nx=position.x;
        int ny= position.y;
        if(position.x >width)
        {
            nx=0;
        }
        if(position.x<0)
        {
            nx=width;
        }
        if(position.y >height)
        {
            ny=0;
        }
        if(position.y < 0)
        {
            ny = height;
        }
        return new Vector2d(nx,ny);
    }

    public float getAnimalEner(Vector2d pos)
    {
        if(fields.containsKey(pos))
        {
            return fields.get(pos).getEnergy();
        }else return 0;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal)
    {

        Field fo = fields.get(oldPosition);
        fo.removeAnimal(animal);
        if(isOccupied(newPosition))
        {
            Field fn = fields.get(newPosition);
            fn.addAnimal(animal);
        }else
        {
            fields.put(newPosition,new Field(animal));
        }
        if(fo.isEmpty())
        {
            fields.remove(oldPosition);
            if(oldPosition.x>=px && oldPosition.x<=width - px && oldPosition.y>=py && oldPosition.y<=height-py)
            {
                free_space_jg.add(oldPosition);
            }
            else
            {
                free_space.add(oldPosition);
            }
        }
    }

    @Override
    public void addedNewPosition(Vector2d newPosition,Animal animal)
    {
        animals_alive.add(animal);
        if(isOccupied(newPosition))
        {
            Field fn = fields.get(newPosition);
            fn.addAnimal(animal);
        }else
        {
            fields.put(newPosition,new Field(animal));
        }

    }



    public boolean isOccupied(Vector2d pos)
    {
        return fields.containsKey(pos);
    }

    public boolean containsAnimal(Vector2d pos)
    {
        return fields.containsKey(pos) && !fields.get(pos).containsGrass();
    }

    public boolean containsBush(Vector2d pos){ return fields.containsKey(pos) && fields.get(pos).containsGrass(); }

    public boolean isChosen(Vector2d pos)
    {
        return fields.containsKey(pos) && fields.get(pos).highlight;
    }

    public boolean isFreeInJungle(Vector2d pos)
    {
        return free_space_jg.contains(pos);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Animal chooseNextAnimal()
    {
        if(animals_alive.size()!=0)
        {
            Animal a = animals_alive.get(chosen);
            fields.get(a.getPosition()).highlight = false;
            chosen=chosen+1;
            if(chosen>=animals_alive.size())
            {
                chosen=0;
            }
            a= animals_alive.get(chosen);
            fields.get(a.getPosition()).highlight = true;
            return a;
        }
        return null;
    }

    public Animal choosePrevAnimal()
    {
        if(animals_alive.size()!=0)
        {
            Animal a = animals_alive.get(chosen);
            fields.get(a.getPosition()).highlight = false;
            chosen=chosen-1;
            if(chosen<0)
            {
                chosen=animals_alive.size()-1;
            }
            a= animals_alive.get(chosen);
            fields.get(a.getPosition()).highlight = true;
            return a;
        }
        return null;
    }

    public int getNumberOfAnimals()
    {
        return animals_alive.size();
    }

    public float getAvgEnergy()
    {
        if(animals_alive.size() ==0)
        {
            return 0;
        }
        float res=0F;
        for(Animal a:animals_alive)
        {
            res+=a.getEnergy();
        }
        res=res/(float) animals_alive.size();
        return res;
    }

    public float getAvgLifeSpan()
    {
        if(animals_dead.size()==0)
        {
            return 0;
        }
        float res=0F;
        for(Animal a:animals_dead)
        {
            res+=a.getDays();
        }
        res=res/(float) animals_dead.size();
        return res;
    }

    public float getAvgChildren()
    {
        if(animals_alive.size() ==0)
        {
            return 0;
        }
        float res=0F;
        for(Animal a:animals_alive)
        {
            res+=a.getNumChildren();
        }
        res=res/(float) animals_alive.size();
        return res;
    }
}
