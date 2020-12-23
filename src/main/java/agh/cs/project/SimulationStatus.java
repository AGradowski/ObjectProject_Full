package agh.cs.project;

public class SimulationStatus
{
    public static boolean running = false;
    public long interval = 500;

    public static void toggle()
    {
        running =!running;
    }
}
