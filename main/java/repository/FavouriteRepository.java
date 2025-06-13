package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.AnalysisResult;
import model.Location;
import util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavouriteRepository
{
    private static final File FILE = new File("favourites.json");

    public static void save(Location location)
    {
        List<Location> list = load();
        if (list == null)
        {
            list = new ArrayList<>();
        }

        if (!list.contains(location))
        {
            list.add(location);
            JsonUtil.writeToFile(FILE, list);
        }
    }

    public static List<Location> load()
    {
        if (!FILE.exists()) return new ArrayList<>();
        try
        {
            return JsonUtil.readFromFile(FILE, new TypeReference<>() {});
        }
        catch (IOException e)
        {
            System.err.println("Error reading JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
