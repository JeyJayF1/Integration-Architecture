package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.bson.Document;

import java.util.ArrayList;

public class ManagePersonal implements ManagePersonalInterface{

    MongoClient mongoClient = new MongoClient("localhost",27017);
    MongoDatabase db = mongoClient.getDatabase("CompanyDatabase2");
    MongoCollection<Document> collection = db.getCollection("salesman");
    public ManagePersonal(){

    }
    @Override
    public void createSalesMan(SalesMan record) {
        Document salesMan = record.toDocument();
        collection.insertOne(salesMan);
    }

    @Override
    public void deleteSalesMan(int sid) {
        collection.deleteOne(new Document("sid", sid));
    }

    @Override
    public void deleteAllSalesMan() {
        collection.deleteMany(new Document());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        int sid = salesMan.getId();

        Document socialPerformanceRecord = record.toDocument();
        collection.updateOne(new Document("sid",sid), Updates.push
                ("PerformanceRecords",socialPerformanceRecord));
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        SalesMan salesmanObject = toSalesMan(salesmanDocument);
        return salesmanObject;
    }

    @Override
    public ArrayList<SalesMan> readAllSalesMen() {
        ArrayList SalesManList = new ArrayList<SalesMan>();
        MongoCursor<Document> cursor = collection.find().cursor();
        while(cursor.hasNext()){
            Document salesmanDocument = cursor.next();
            SalesMan salesManObject = toSalesMan(salesmanDocument);
            SalesManList.add(salesManObject);
        }
        return SalesManList;
    }

    @Override
    public ArrayList<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        int sid = salesMan.getId();
        ArrayList PerformanceRecords = new ArrayList<SocialPerformanceRecord>();
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        ArrayList<Document> perfromanceRecordDocument = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        for (Document record: perfromanceRecordDocument){
            SocialPerformanceRecord record1 = toSocialPerformanceRecord(record);
            PerformanceRecords.add(record1);
        }
        return PerformanceRecords;
    }

    @Override
    public SocialPerformanceRecord readLastSocialPerformanceRecord(SalesMan salesmMan) {
        return null;
    }

    @Override
    public SocialPerformanceRecord readByYearSocialPerformanceRecord(SalesMan salesMan, int year) {
        return null;
    }

    public SalesMan toSalesMan(Document salesman){
        String firstname = salesman.getString("firstname");
        String lastname = salesman.getString("lastname");
        int sid = salesman.getInteger("sid");

        return new SalesMan(firstname, lastname, sid);
    }

    public SocialPerformanceRecord toSocialPerformanceRecord(Document record){
        Integer leadership = record.getInteger("leadership");
        Integer openness = record.getInteger("openness");
        Integer behaviour = record.getInteger("behaviour");
        Integer attitude = record.getInteger("attitude");
        Integer communication = record.getInteger("communication");
        Integer integrity = record.getInteger("integrity");
        Integer year = record.getInteger("leadership");

        return new SocialPerformanceRecord(leadership, openness, behaviour, attitude, communication, integrity, year);
    }

}
