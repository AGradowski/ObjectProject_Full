package agh.cs.project;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;


public class FileLoader
{
    public int width;
    public int height;
    public float jungle_ratio;
    public float start_energy;
    public float move_energy;
    public float plant_energy;
    public int animal_number;

    public int scale=0;

    public FileLoader() throws Exception
    {
        String root = System.getProperty("user.dir");
        String FileName= "agh/cs/project/source.json";
        String filePath = root+ File.separator+"src"+File.separator+"main" +File.separator+"java" + File.separator + FileName;
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(new java.io.FileReader(filePath));

        JSONObject file = (JSONObject) object;

        width = ((Long) file.get("width")).intValue();
        height = ((Long) file.get("height")).intValue();
        jungle_ratio=((Double) file.get("jungle_ratio")).floatValue();

        animal_number = ((Long) file.get("animal_number")).intValue();
        move_energy= ((Long) file.get("move_energy")).intValue();
        plant_energy = ((Long) file.get("plant_energy")).intValue();
        start_energy = ((Long) file.get("start_energy")).intValue();
        scale = ((Long) file.get("scale")).intValue();
        if (width <= 0 || height <= 0 || jungle_ratio <= 0)
            throw new RuntimeException("Wrong Data! Width, height and jungleRatio must be positive values");
    }
}
