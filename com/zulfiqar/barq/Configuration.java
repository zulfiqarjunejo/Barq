package com.zulfiqar.barq;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public record Configuration(
        int port,
        String blogName,
        String headerFile,
        String footerFile,
        String contentDir,
        String workingDir
) {
    public static Configuration fromArgs(Args args) throws IOException {
        var workingDir = System.getProperty("user.dir");
        var configFilePath = Paths.get(workingDir, args.configFile).toString();
        var configFile = new File(configFilePath);

        var content = Files.readString(configFile.toPath());
        var contentAsJson = new JSONObject(content);

        var blogName = contentAsJson.getString("blogName");
        var headerFile = contentAsJson.getString("headerFile");
        var footerFile = contentAsJson.getString("footerFile");
        var contentDir = contentAsJson.getString("contentDir");

        return new Configuration(
                args.port,
                blogName,
                headerFile,
                footerFile,
                contentDir,
                workingDir
        );
    }
}
