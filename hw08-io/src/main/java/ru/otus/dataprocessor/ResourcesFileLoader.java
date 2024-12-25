package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import java.util.List;
import jakarta.json.JsonArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;


public class ResourcesFileLoader implements Loader {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws FileProcessException {
        List<Measurement> measurements;
        ObjectMapper objectMapper = new ObjectMapper();
        try (var jsonReader =
                     Json.createReader(getClass().getClassLoader().getResourceAsStream(fileName))) {
            JsonArray jsonArray = jsonReader.readArray();
            measurements = objectMapper.readValue(jsonArray.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage());
            throw new FileProcessException(ex);
        }
        return measurements;
    }
}
