package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.bson.Document;

import java.util.List;

public class ManagePersonal implements ManagePersonalInterface{

    private MongoDatabase db;
    private MongoCollection<Document> salesmenCollection;

    public ManagePersonal(){
        try(MongoClient mongoClient = new MongoClient("localhost",27017)){
            this.db = mongoClient.getDatabase("CompanyDatabase");
            this.salesmenCollection = db.getCollection("salesmen");
        }
    }
    @Override
    public void createSalesMan(SalesMan record) {
        Document salesMan = record.toDocument();
        salesmenCollection.insertOne(salesMan);
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

    }

    @Override
    public SalesMan readSalesMan(int sid) {
        return null;
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        return null;
    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        return null;
    }

    @Override
    public SocialPerformanceRecord readLastSocialPerformanceRecord(SalesMan salesmMan) {
        return null;
    }

    @Override
    public SocialPerformanceRecord readByYearSocialPerformanceRecord(SalesMan salesMan, int year) {
        return null;
    }
}
