package util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> void writeToFile(File file, T data)
    {
        try
        {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static <T> T readObjectFromFile(File file, Class<T> clazz)
    {
        try
        {
            return mapper.readValue(file, clazz);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readFromFile(File file, TypeReference<T> typeReference) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, typeReference);
    }

    public static <T> List<T> readFromFile(File file, Class<T> clazz)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            if (!file.exists() || file.length() == 0)
            {
                return new ArrayList<>();
            }
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(file, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
