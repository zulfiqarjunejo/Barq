package com.zulfiqar.barq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

public class HtmlRenderer {
    private final String contentDir;
    private final String workingDir;
    private final String header;
    private final String footer;

    public HtmlRenderer(Configuration configuration) throws IOException {
        this.contentDir = configuration.contentDir();
        this.workingDir = configuration.workingDir();
        this.header = FileUtils.replaceBlogName(
                FileUtils.readHtmlFile(
                        Paths.get(configuration.workingDir(), configuration.headerFile()).toString()
                ),
                configuration.blogName()
        );
        this.footer = FileUtils.replaceBlogName(
                FileUtils.readHtmlFile(
                        Paths.get(configuration.workingDir(), configuration.footerFile()).toString()
                ),
                configuration.blogName()
        );
    }

    public void render(OutputStream out, BufferedReader in) {
        try {
            var message = in.readLine();
            if (message == null) {
                return;
            }

            var articleName = getArticleName(message);

            var articleContent = "";
            try {
                articleContent = FileUtils.readHtmlFile(Paths.get(this.workingDir, this.contentDir, articleName).toString());
            } catch (FileNotFoundException e) {
                articleContent = "OOPS! Article not found :(";
            }

            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html \r\n" +
                    "Content-Length: <calculated-length>" + "\r\n" +
                    "\r\n" +
                    header +
                    articleContent +
                    footer;

            out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("HtmlRenderer.render(): " + e.getMessage());
        }
    }

    private static String getArticleName(String message) {
        var path = message.split(" ")[1];

        if (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }

        if (path != null && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        var fileName = "";
        if (Objects.equals(path, "")) {
            fileName = "index.html";
        } else if (!path.endsWith(".html")) {
            fileName = path + ".html";
        } else {
            fileName = path;
        }
        return fileName;
    }
}
