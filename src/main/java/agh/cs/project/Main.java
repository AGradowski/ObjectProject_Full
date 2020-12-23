package agh.cs.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Main extends Application {


    private int transformMapVectorX=0;
    private int transformMapVectorY = 100;

    private Simulation sim1;
    private Simulation sim2;

    private Group root;
    private Visualizer vis;
    @Override
    public void start(Stage stage)
    {
        try {
            FileLoader file = new FileLoader();


            sim1 = new Simulation(file.width, file.height, file.jungle_ratio, file.start_energy, file.move_energy, file.plant_energy, file.animal_number);
            sim2 = new Simulation(file.width, file.height, file.jungle_ratio, file.start_energy, file.move_energy, file.plant_energy, file.animal_number);
            vis = new Visualizer(file.scale);
        }catch(Exception e)
        {
            System.out.println("Nie mozna znalec pliku");
            sim1 = new Simulation(10,10,0.5F,100F,1F,1F,0);
            sim2 = new Simulation(10,10,0.5F,100F,1F,1F,0);
            vis = new Visualizer(10);
        }
        SimulationStatus simulationStatus = new SimulationStatus();


        Thread thread = new Thread(() -> {
            Runnable runnable = this::update;
            while (true) {
                try {
                    Thread.sleep(simulationStatus.interval);
                } catch (InterruptedException ignore) {
                }
                if (simulationStatus.running) {
                    Platform.runLater(runnable);
                }

            }
        });

        thread.setDaemon(true);
        thread.start();
        root = vis.visualize(transformMapVectorX,transformMapVectorY,sim1,sim2);
        vis.update_vis(root,transformMapVectorX+sim1.getMap().getWidth()*vis.scale+2*vis.scale,transformMapVectorY,sim2);
        Scene scene = new Scene(root, 1100, 800);
        stage.setScene(scene);
        stage.show();

    }

    void update()
    {
        sim1.nextDay();
        sim2.nextDay();
        vis.clear(root);
        vis.update_vis(root,transformMapVectorX,transformMapVectorY,sim1);
        vis.update_vis(root,transformMapVectorX+sim1.getMap().getWidth()*vis.scale+2*vis.scale,transformMapVectorY,sim2);
        vis.add_buttons(root,sim1,transformMapVectorX,transformMapVectorY);
        vis.add_buttons(root,sim2,transformMapVectorX+sim1.getMap().getWidth()*vis.scale+2*vis.scale,transformMapVectorY);
        vis.add_default(root);
    }
    public void updateStopped()
    {
        vis.clear(root);
        vis.update_vis(root,transformMapVectorX,transformMapVectorY,sim1);
        vis.update_vis(root,transformMapVectorX+sim1.getMap().getWidth()*vis.scale+2*vis.scale,transformMapVectorY,sim2);
        vis.add_buttons(root,sim1,transformMapVectorX,transformMapVectorY);
        vis.add_buttons(root,sim2,transformMapVectorX+sim1.getMap().getWidth()*vis.scale+2*vis.scale,transformMapVectorY);
        vis.add_default(root);
    }

    public static void main(String[] args)
    {
        launch();
    }

}
