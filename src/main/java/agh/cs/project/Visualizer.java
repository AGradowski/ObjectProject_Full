package agh.cs.project;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class Visualizer
{
    private Button buttonStart= new Button("Start/Stop");
    public int scale;
    private Simulation sim1;
    private Simulation sim2;
    private int transX;
    private int transY;

    public Visualizer(int scale)
    {
        this.scale=scale;
        buttonStart.setOnAction(e->SimulationStatus.toggle());
    }

    public Group visualize(int transformX, int transformY, Simulation sim, Simulation sim2)
    {
        sim1=sim;
        transX=transformX;
        transY=transformY;
        this.sim2=sim2;
        WorldMap map = sim.getMap();
            Group root = new Group();

        for(int x =0; x<= map.getWidth();x++)
        {
            for(int y =0; y<= map.getHeight();y++)
            {
                Vector2d pos = new Vector2d(x,y);
                if(map.isChosen(pos))
                {
                    Rectangle chosen = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    chosen.setFill(Color.rgb(0,0,255));
                }else if(map.isOccupied(pos))
                {
                    Rectangle animal = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    float energy = map.getAnimalEner(pos);
                    float new_energy=0F;
                    if(energy<100)
                    {
                        new_energy=energy/100F;
                    }else
                    {
                        new_energy = 1F;
                    }

                    animal.setFill(Color.rgb(255,0,255,(double)new_energy));
                    root.getChildren().add(animal);
                }
                else if(map.containsBush(pos))
                {
                    Rectangle plant = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    plant.setFill(Color.rgb(0,120,12));
                    root.getChildren().add(plant);
                }else if(map.isFreeInJungle(pos))
                {
                    Rectangle emptyJg =new Rectangle(transformX+x*scale, transformY+y*scale,scale,scale);
                    emptyJg.setFill(Color.rgb(0,255,0));
                    root.getChildren().add(emptyJg);
                }
                else
                {
                    Rectangle empty = new Rectangle(transformX+x*scale, transformY+y*scale,scale,scale);
                    empty.setFill(Color.rgb(245,222,179));
                    root.getChildren().add(empty);
                }
            }
        }
        Button next = new Button("Next Animal");
        next.setLayoutX(transformX+100);
        next.setLayoutY(transformY-25);
        next.setOnAction(e->chooseNextAnimal(root,sim));
        root.getChildren().add(next);
        Button prev = new Button("Prev Animal");
        prev.setLayoutX(transformX);
        prev.setLayoutY(transformY-25);
        prev.setOnAction(e->choosePrevAnimal(root,sim));
        root.getChildren().add(prev);

        root.getChildren().add(buttonStart);



        return root;
    }

    public void clear(Group root)
    {
        root.getChildren().clear();
    }

    public void update_vis(Group root,int transformX, int transformY,Simulation sim)
    {
        WorldMap map = sim.getMap();
        for(int x =0; x<= map.getWidth();x++)
        {
            for(int y =0; y<= map.getHeight();y++)
            {
                Vector2d pos = new Vector2d(x,y);
                if(map.isChosen(pos))
                {
                    Rectangle chosen = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    chosen.setFill(Color.rgb(0,0,255));
                    root.getChildren().add(chosen);
                }else if(map.containsAnimal(pos))
                {
                    Rectangle animal = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    float energy = map.getAnimalEner(pos);
                    float new_energy=0F;
                    if(energy<100)
                    {
                        new_energy=energy/100F;
                        if(new_energy<0)
                        {
                            new_energy=0.01F;
                        }
                    }else
                    {
                        new_energy = 1F;
                    }

                    animal.setFill(Color.rgb(255,0,255,(double)new_energy));
                    root.getChildren().add(animal);
                }
                else if(map.containsBush(pos))
                {
                    Rectangle plant = new Rectangle(transformX+x*scale,transformY+y*scale,scale,scale);
                    plant.setFill(Color.rgb(0,120,12));
                    root.getChildren().add(plant);
                }
                else if(map.isFreeInJungle(pos))
                {
                    Rectangle emptyJg =new Rectangle(transformX+x*scale, transformY+y*scale,scale,scale);
                    emptyJg.setFill(Color.rgb(0,255,0));
                    root.getChildren().add(emptyJg);
                }
                else
                {
                    Rectangle empty = new Rectangle(transformX+x*scale, transformY+y*scale,scale,scale);
                    empty.setFill(Color.rgb(245,222,179));
                    root.getChildren().add(empty);
                }
            }
        }
        Button next = new Button("Next Animal");
        next.setLayoutX(transformX+100);
        next.setLayoutY(transformY-25);
        next.setOnAction(e->chooseNextAnimal(root,sim));
        root.getChildren().add(next);
        Button prev = new Button("Prev Animal");
        prev.setLayoutX(transformX);
        prev.setLayoutY(transformY-25);
        prev.setOnAction(e->choosePrevAnimal(root,sim));
        root.getChildren().add(prev);
    }

    public void updateText(Group root,  Animal animal)
    {
        if(animal != null) {
            clear(root);
            update_vis(root, transX, transY, sim1);
            update_vis(root, transX + sim1.getMap().getWidth() * scale + 2 * scale, transY, sim2);
            add_buttons(root, sim1, transX, transY);
            add_buttons(root, sim2, transX + sim1.getMap().getWidth() * scale + 2 * scale, transY);
            add_default(root);
            Text chosen = new Text("Chosen Animal");
            chosen.setFont(new Font(20));
            chosen.setLayoutY(transY + sim1.getMap().getHeight()*scale+scale*15);
            root.getChildren().add(chosen);

            Text genes = new Text("Genes =" + animal.getDna().toString());
            genes.setFont(new Font(20));
            genes.setLayoutY(transY + sim1.getMap().getHeight()*scale+scale*17);
            root.getChildren().add(genes);

            Text position = new Text("Position ="+animal.getPosition().toString());
            position.setFont(new Font(20));
            position.setLayoutY(transY + sim1.getMap().getHeight()*scale+scale*19);
            root.getChildren().add(position);

            Text children = new Text("Children ="+animal.getNumChildren());
            children.setFont(new Font(20));
            children.setLayoutY(transY + sim1.getMap().getHeight()*scale+scale*19);
            children.setLayoutX(250);
            root.getChildren().add(children);
        }
    }

    public void add_buttons(Group root,Simulation sim,int transX, int transY)
    {
        root.getChildren().addAll(sim.getButtons(transX,transY,scale));
    }

    public void add_default(Group root)
    {
        root.getChildren().add(buttonStart);
    }

    public void chooseNextAnimal(Group root, Simulation sim)
    {
        Animal a =sim.nextAnimal();
        updateText(root,a);
    }

    public void choosePrevAnimal(Group root, Simulation sim)
    {
        Animal a =sim.previousAnimal();
        updateText(root,a);
    }

}
