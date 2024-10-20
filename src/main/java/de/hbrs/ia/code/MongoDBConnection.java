package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnection {
    public static void main(String[] args) {

        MongoDatabase db = mongoClient.getDatabase("Database");


        MongoCursor<String> dbsCursor = mongoClient.listDatabaseNames().iterator();
        while (dbsCursor.hasNext()) {
            System.out.println(dbsCursor.next());
        }

    }
}
