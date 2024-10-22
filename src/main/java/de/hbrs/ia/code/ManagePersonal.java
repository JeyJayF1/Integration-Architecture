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

    protected  int sid  ;
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
        this.sid = salesMan.getId();

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
        this.sid = salesMan.getId();
        ArrayList PerformanceRecords = new ArrayList<SocialPerformanceRecord>();
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        ArrayList<Document> performanceRecordDocument = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        for (Document record: performanceRecordDocument){
            SocialPerformanceRecord record1 = toSocialPerformanceRecord(record);
            PerformanceRecords.add(record1);
        }
        return PerformanceRecords;
    }

    @Override
    public SocialPerformanceRecord readLastSocialPerformanceRecord(SalesMan salesmMan) {

        this.sid = salesmMan.getId();
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        ArrayList<Document> performanceRecordDocuments = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        if(performanceRecordDocuments == null || performanceRecordDocuments.isEmpty()){
            return null;
        }

        performanceRecordDocuments.sort((doc1, doc2) -> doc2.getInteger("year").compareTo(doc1.getInteger("year"))); //Sort by year descending
        Document latestRecord = performanceRecordDocuments.get(0); // Get the last one (latest year)
        return toSocialPerformanceRecord(latestRecord);
    }

    @Override
    public SocialPerformanceRecord readByYearSocialPerformanceRecord(SalesMan salesMan, int year) {

        this.sid = salesMan.getId();
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        ArrayList<Document> performanceRecordDocuments = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        if (performanceRecordDocuments == null || performanceRecordDocuments.isEmpty()) {
            return null;
        }

        for (Document record : performanceRecordDocuments) {
            if (record.getInteger("year") == year) {
                return toSocialPerformanceRecord(record); // Return the matching record
            }
        }

        return null; // No record found for the given year

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
        Integer year = record.getInteger("year");

        return new SocialPerformanceRecord(leadership, openness, behaviour, attitude, communication, integrity, year);
    }

}
