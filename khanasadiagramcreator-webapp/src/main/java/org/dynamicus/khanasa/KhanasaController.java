package org.dynamicus.khanasa;

import freemarker.template.Configuration;
import org.dynamicus.bean.DatabaseCredentialBean;
import org.dynamicus.enumuration.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

public class KhanasaController {

    private static final Logger log = LoggerFactory.getLogger(KhanasaController.class);

    public static void main(String[] args) {

        new KhanasaController().khanasaDiagramCreator();
    }

    public void khanasaDiagramCreator(){

        port(4567);

        staticFileLocation("/public");

        get("/", (request, response) -> {
            response.redirect("/index.html");
            return "";
        });

        get("/index.html", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Welcome to Khanasa diagram creator!");
            attributes.put("databaseTypeMap", createDatabaseTypeMap());

            return new ModelAndView(attributes, "khanasadiagramcreator.ftl");
        }, new FreeMarkerEngine(createDefaultConfiguration()));

        post("/submit.html", (request, response) -> {
            String user = request.queryParams("user");
            String password = request.queryParams("password");
            String ip = request.queryParams("ip");
            String port  = request.queryParams("port");
            int dbType  = Integer.parseInt(request.queryParams("dbType"));
            DatabaseType databaseType = DatabaseType.fromNumberValue(dbType);
            log.debug("USER : " + user + ", PASSWORD : " + password + ", DB TYPE : " + dbType);

            //Create database credential
            DatabaseCredentialBean dbCredential = createCredential(user, password, ip, port, databaseType);
            log.debug("Connecting database..");
            new KhanasaDiagramCreator().khanasaDiagramCreator(dbCredential);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Diagram created");

            return new ModelAndView(attributes, "khanasa.ftl");
        }, new FreeMarkerEngine(createDefaultConfiguration()));

        get("/images/khanasadiagram.png", (request, response) -> {
            try {
                ServletOutputStream output = response.raw().getOutputStream();
                File file = new File
                        ("/tmp/khanasadiagram.png");
                if (!file.exists()) {
                    throw new RuntimeException("File not found");
                }
                InputStream is = new FileInputStream(file);
                int read = 0;
                while ((read = is.read()) >= 0) {
                    output.write(read);
                }
                output.flush();

            } catch (IOException e) {
                throw new RuntimeException("Trouble", e);
            }
            return null;
        });

    }

    private Configuration createDefaultConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(getClass(), "");
        return configuration;
    }

    private DatabaseCredentialBean createCredential(String user,
                                                String password,
                                                String ip,
                                                String port,
                                                DatabaseType dbType) {
        DatabaseCredentialBean dbCredential = new DatabaseCredentialBean();
        dbCredential.setUser(user);
        dbCredential.setPassword(password);
        dbCredential.setJdbcDriver(new KhanasaJdbcProperties().getJdbcDriver(dbType));
        dbCredential.setJdbcUrl(new KhanasaJdbcProperties().getJdbcUrl(dbType, user, ip, port));

        log.debug("JDBC DRIVER : " + new KhanasaJdbcProperties().getJdbcDriver(dbType));

        return dbCredential;
    }

    public Map<String, String> createDatabaseTypeMap() {
        Map<String, String> databaseTypeMap = new LinkedHashMap<String, String>();
        for (DatabaseType dbType : DatabaseType.values()) {
            databaseTypeMap.put(String.valueOf(dbType.toNumberValue()), dbType.toTextValue());
        }
        return databaseTypeMap;
    }

}
