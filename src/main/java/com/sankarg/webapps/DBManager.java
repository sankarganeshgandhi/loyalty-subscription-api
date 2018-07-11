package com.sankarg.webapps;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBManager {
    private final static Logger logger = LoggerFactory.getLogger(DBManager.class);

    private final static String APP_DS_DB = "webapps";

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    public static void init(List<Class> codecClassList) throws Exception {
        if (database == null) {
            List<Codec<?>> codecObjectList = new ArrayList<>();
            for (Class codecClass : codecClassList) {
                codecObjectList.add((Codec<?>) codecClass.newInstance());
            }
            CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(codecObjectList), MongoClient.getDefaultCodecRegistry()
            );

            MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();
            MongoClientURI uri = new MongoClientURI(AppConfig.get(AppConfig.APP_DS_URL),
                MongoClientOptions.builder(options));
            mongoClient = new MongoClient(uri);

            database = mongoClient.getDatabase(APP_DS_DB);
            logger.info("connected with database" + AppConfig.get(AppConfig.APP_DS_URL));
        } else {
            logger.info("connected with database" + AppConfig.get(AppConfig.APP_DS_URL));
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        mongoClient.close();
        database = null;
        logger.info("connection with " + AppConfig.get(AppConfig.APP_DS_URL) + " has been closed");
    }

    public static String getJSON(Object entity) {
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonString;
    }

    public static String getNextId() {
        return UUID.randomUUID().toString();
    }

    public static Codec<Document> getDefaultCodec() {
        return MongoClient.getDefaultCodecRegistry().get(Document.class);
    }
}