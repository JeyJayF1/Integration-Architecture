package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBConnection {
    public static void main(String[] args) {

        SalesMan salesMan1 = new SalesMan("Test","User",500);

        SocialPerformanceRecord record1= new SocialPerformanceRecord(
        1,1,1,1,1,1,2022);
        SocialPerformanceRecord record2= new SocialPerformanceRecord(
                1,1,1,1,1,1,2024);
        SocialPerformanceRecord record3= new SocialPerformanceRecord(
                1,1,1,1,1,1,2025);

        ManagePersonal controller = new ManagePersonal();

        controller.createSalesMan(salesMan1);
        controller.addSocialPerformanceRecord(record1,salesMan1);
        controller.addSocialPerformanceRecord(record2,salesMan1);
        controller.addSocialPerformanceRecord(record3,salesMan1);

        ArrayList<SocialPerformanceRecord> records = controller.readSocialPerformanceRecord(salesMan1);
        for(SocialPerformanceRecord record: records){
            System.out.println(record);
        }




    }
}
