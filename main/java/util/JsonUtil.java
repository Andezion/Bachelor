package util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> void writeToFile(File file, T data) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T readFromFile(File file, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, typeReference);
    }

    public static <T> List<T> readFromFile(File file, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>(); // Файл не существует или пустой — вернуть пустой список
            }
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(file, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // В случае ошибки тоже вернуть пустой список
        }
    }
}
