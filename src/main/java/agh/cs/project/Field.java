package agh.cs.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Field
{
    private final Vector2d position;
    private final List<Animal> animals = new ArrayList<>();
    private boolean hasGrass;
    public boolean highlight =false;

    public Field(Vector2d pos)
    {
        hasGrass = true;
        position = pos;
    }

    public Field(Animal animal)
    {
        animals.add(animal);
        hasGrass = false;
        position = animal.getPosition();
    }

    public void addAnimal(Animal animal)
    {
        if(animals.size()==0)
        {
            animals.add(animal);
            return;
        }
        int i = 0;
        while(i <animals.size() &&animals.get(i).getEnergy() > animal.getEnergy() )
        {
            i++;
        }
        animals.add(i,animal);
    }

    public void removeAnimal(Animal animal)
    {
        animals.remove(animal);
    }

    public void removeGrass()
    {
        hasGrass = false;
    }


    public void clean(Animal animal)
    {
        animals.remove(animal);

    }

    public void mate(float start_energy, WorldMap map)
    {
        if(animals.size()>=2)
        {
            Animal animal1 = animals.get(0);
            Animal animal2 = animals.get(1);
            if(animal1.getEnergy()>=(start_energy/2)&&animal2.getEnergy()>=(start_energy/2))
            {
                float son_energy = animal1.getEnergy()/4 + animal2.getEnergy()/4;

                animal1.deplete_energy(animal1.getEnergy()/4);
                animal1.addChild();
                animal2.deplete_energy(animal2.getEnergy()/4);
                animal2.addChild();
                MapDirection rotator = MapDirection.NORTH;
                Vector2d res = new Vector2d(0,0);
                boolean found=false;
                for(int i =0; i<9; i++)
                {
                    if(!map.containsAnimal(map.nextMove(position.add(rotator.toUnitVector()))))
                    {
                        res = map.nextMove(position.add(rotator.toUnitVector()));
                        found = true;
                        break;
                    }else
                    {
                        rotator=rotator.next();
                    }
                }
                if(!found)
                {
                    res = map.nextMove(position.add(MapDirection.random().toUnitVector()));
                }
                Animal son = new Animal(map,MapDirection.random(), res, son_energy, new DNA(animal1, animal2));

            }
        }
    }

    public boolean eat(float plant_energy)
    {
        if(hasGrass && !animals.isEmpty())
        {
            int a=0;
            while(a+1< animals.size() && animals.get(a).getEnergy() == animals.get(a+1).getEnergy())
            {
                a++;
            }
            a++;

            for(int i =0; i<a;i++)
            {
                animals.get(i).eat(plant_energy / a);
            }
            hasGrass = false;
            return true;
        }
        return false;
    }

    public float getEnergy()
    {
        if(animals.size()!=0) {
            return animals.get(0).getEnergy();
        }else return 0;
    }

    public boolean isEmpty()//neither grass nor animal is occupying this space
    {
        return  !hasGrass&&animals.size()==0;//TODO not has grass
    }

    public boolean containsGrass()
    {
        return hasGrass;
    }

    public Vector2d getPosition() {
        return position;
    }
}
