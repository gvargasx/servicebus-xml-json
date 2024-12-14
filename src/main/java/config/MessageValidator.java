package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MessageValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isJson(String message) {
        try {
            objectMapper.readTree(message);
            return true; // É JSON válido
        } catch (Exception e) {
            return false; // Não é JSON
        }
    }

    public static boolean isXml(String message) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new java.io.ByteArrayInputStream(message.getBytes()));
            return true; // É XML válido
        } catch (Exception e) {
            return false; // Não é XML
        }
    }
}

