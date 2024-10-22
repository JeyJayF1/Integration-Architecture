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
    MongoDatabase db = mongoClient.getDatabase("CompanyDatabase");
    MongoCollection<Document> collection = db.getCollection("salesman");
    public ManagePersonal(){

    }
    // create a SalesMan Document in the collection by giving a SalesMan object
    @Override
    public void createSalesMan(SalesMan salesMan) {
        Document salesManDocument = salesMan.toDocument();
        collection.insertOne(salesManDocument);
    }

    //delete a SalesMan in the collection by sid
    @Override
    public void deleteSalesMan(int sid) {
        collection.deleteOne(new Document("sid", sid));
    }

    //delete all SalesMan Documents in the collection
    @Override
    public void deleteAllSalesMan() {
        collection.deleteMany(new Document());
    }

    // add a SocialPerformanceRecord to a SalesMan, by searching for the SalesMan by sid
    // and adding a SocialPerformanceRecord into the records ArrayList of the SalesMan
    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        int sid = salesMan.getId();

        Document socialPerformanceRecord = record.toDocument();
        collection.updateOne(new Document("sid",sid), Updates.push
                ("PerformanceRecords",socialPerformanceRecord));
    }

    // read a SalesMan by sid
    @Override
    public SalesMan readSalesMan(int sid) {
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();
        SalesMan salesmanObject = toSalesMan(salesmanDocument);
        return salesmanObject;
    }

    // read all SalesMan in the Collection
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

    // read all SocialPerformanceRecord of a specific SalesMan
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

    // read the last added SocialPerformanceRecord of a specific salesMan
    @Override
    public SocialPerformanceRecord readLastSocialPerformanceRecord(SalesMan salesMan) {
        int sid = salesMan.getId();
        Document salesmanDocument = collection.find(new Document("sid", sid)).first();

        ArrayList<Document> performanceRecordDocument = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        Document lastSocialPerformanceRecord = performanceRecordDocument.get(performanceRecordDocument.size() - 1);

        return toSocialPerformanceRecord(lastSocialPerformanceRecord);

    }

    // read a year specific SocialPerformanceRecord of a specific salesMan
    @Override
    public SocialPerformanceRecord readByYearSocialPerformanceRecord(SalesMan salesMan, int year) {
        int sid = salesMan.getId();
        Document salesmanDocument = collection.find(new Document("sid",sid)).first();

        ArrayList<Document> performanceRecordDocument = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");
        Document yearSocialPerformanceRecord = null;
        if(performanceRecordDocument == null || performanceRecordDocument.isEmpty()){
            return null;
        }
        for(Document performanceRecord : performanceRecordDocument){
            if(performanceRecord.getInteger("year") == year){
                yearSocialPerformanceRecord = performanceRecord;
                break;
            }
        }
        return toSocialPerformanceRecord(yearSocialPerformanceRecord);
    }

    @Override
    public void deleteByYearSocialPerformanceRecord(SalesMan salesMan, int year) {
        int sid = salesMan.getId();
        Document salesmanDocument = collection.find(new Document("sid",sid)).first();
        Document remove = new Document("$pull",new Document("PerformanceRecords",new Document("year",year)));

        collection.updateOne(salesmanDocument, remove);

    }

    @Override
    public void deleteLastSocialPerformanceRecord(SalesMan salesMan){
        int sid = salesMan.getId();
        Document salesmanDocument = collection.find(new Document("sid",sid)).first();
        ArrayList<Document> performanceRecordDocument = (ArrayList<Document>) salesmanDocument.get("PerformanceRecords");

        if(performanceRecordDocument == null || performanceRecordDocument.isEmpty()){
            System.out.println("No PerformanceRecords found");
        }else{
            Document lastPerformanceRecord = performanceRecordDocument.get(performanceRecordDocument.size() - 1);
            Document remove = new Document("$pull",new Document("PerformanceRecords", lastPerformanceRecord));

            collection.updateOne(salesmanDocument, remove);
        }
    }

    // methode for turning a SalesMan Document into a SalesMan Object
    private SalesMan toSalesMan(Document salesman){
        String firstname = salesman.getString("firstname");
        String lastname = salesman.getString("lastname");
        int sid = salesman.getInteger("sid");

        return new SalesMan(firstname, lastname, sid);
    }

    // methode for turning a SocialPerformanceRecord Document into a SocialPerformanceRecord Object
    private SocialPerformanceRecord toSocialPerformanceRecord(Document record){
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
