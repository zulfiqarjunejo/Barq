package com.zulfiqar.barq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static String readHtmlFile(String filePath) throws IOException {
        var content = new StringBuilder();

        try (var br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

    public static String replacePlaceholder(String template, String placeholder,  String value) {
        return template.replace(placeholder, value);
    }

    public static String replaceBlogName(String template, String blogName) {
        return replacePlaceholder(template, "{BlogName}", blogName);
    }
}
