package factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import enums.PayloadPaths;
import javax.imageio.stream.FileImageInputStream;
import java.io.File;
import java.io.IOException;

public class PojoFactory<T> {
    public T createPojoFactory(Class<T> pojoClass, PayloadPaths payloadPath) {
        String filePath = payloadPath.getParam();
        if (filePath != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(new FileImageInputStream(new File(filePath)), pojoClass);
            } catch (IOException e) {
                System.err.println("Falha ao fabricar objeto pojo " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Tipo de depósito inválido: " + payloadPath);
        }
        return null;
    }
}
