package test;

import de.hbrs.ia.code.ManagePersonal;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HighPerformanceTest {
    private ManagePersonal controller;

    /**
     * Attention: You might update the version of the Driver
     * for newer version of MongoDB!
     * This tests run with MongoDB 4.2.17 Community
     */
    @BeforeEach
    void setUp() {
        // Setting up the connection to a local MongoDB with standard port 27017
        // must be started within a terminal with command 'mongod'.
        controller = new ManagePersonal();
    }


    @Test
    void insertAndReadSalesManMoreObjectOriented() {
        // CREATE (Storing) the salesman business object
        // Using setter instead
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90444 );

        // ... now storing the object
        controller.createSalesMan(salesMan);

        // READ (Finding) the stored Documnent
        // Mapping Document to business object would be fine...
        SalesMan salesManObject = controller.readSalesMan(90444);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        System.out.println("___________________________________");

        // Assertion
        Integer sid = salesManObject.getId();
        assertEquals( 90444 , sid );

        // Deletion
        controller.deleteAllSalesMan();
    }

    @Test
    void insertAndReadSocialPerformanceRecordIntoSalesMan(){
        //CREATE the salesman object
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90445 );
        //CREATE the SocialPerformanceRecord object
        SocialPerformanceRecord record = new SocialPerformanceRecord(1,2, 3, 4,5,6,2024);

        // UPDATE the SalesMan by adding a PerformanceRecord
        controller.createSalesMan(salesMan);
        controller.addSocialPerformanceRecord(record,salesMan);

        // READ the Salesman
        SalesMan salesManObject = controller.readSalesMan(90445);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        // READ the social PerformanceRecord
        SocialPerformanceRecord recordObject = controller.readLastSocialPerformanceRecord(salesMan);
        System.out.println("Printing the object: " + "\n" + recordObject);

        System.out.println("___________________________________");

        // Assertion
        Integer sid = salesManObject.getId();
        assertEquals(90445,sid);

        Integer year = recordObject.getYear();
        assertEquals(2024,year);

        // Deletion
        controller.deleteAllSalesMan();
    }

    @Test
    void insertAndReadSocialPerformanceRecordIntoSalesManByYear(){
        //CREATE the salesman object
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90446 );
        //CREATE the SocialPerformanceRecord object
        SocialPerformanceRecord record1 = new SocialPerformanceRecord(1,2, 3, 4,5,6,2024);
        SocialPerformanceRecord record2 = new SocialPerformanceRecord(2,2, 3, 5,5,6,2025);
        SocialPerformanceRecord record3 = new SocialPerformanceRecord(2,2, 5, 1,2,2,2029);

        // UPDATE the SalesMan by adding a PerformanceRecord
        controller.createSalesMan(salesMan);
        controller.addSocialPerformanceRecord(record1,salesMan);
        controller.addSocialPerformanceRecord(record2,salesMan);
        controller.addSocialPerformanceRecord(record3,salesMan);

        // READ the Salesman
        SalesMan salesManObject = controller.readSalesMan(90446);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        // READ the social PerformanceRecord
        SocialPerformanceRecord recordObject = controller.readByYearSocialPerformanceRecord(salesMan,2025);
        System.out.println("Printing the object: " + "\n" + recordObject);

        System.out.println("___________________________________");

        // Assertion
        Integer sid = salesManObject.getId();
        assertEquals(90446,sid);

        Integer year = recordObject.getYear();
        assertEquals(2025,year);

        // Deletion
        controller.deleteAllSalesMan();
    }

    @Test
    void deleteSalesMan(){
        // CREATE (Storing) the salesman business object
        // Using setter instead
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90447 );

        // ... now storing the object
        controller.createSalesMan(salesMan);

        // READ (Finding) the stored Documnent
        // Mapping Document to business object would be fine...
        SalesMan salesManObject = controller.readSalesMan(90447);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        System.out.println("___________________________________");

        // DELETE the salesMan out of the Collection
        controller.deleteSalesMan(90447);

        // Assertion that you cant read the deleted Salesman
        Exception exception = assertThrows(Exception.class, () -> {
            controller.readSalesMan(90447);
        });

        // Deletion
        controller.deleteAllSalesMan();
    }

    @Test
    void deleteSocialPerformanceRecordByYear(){
        //CREATE the salesman object
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90448 );
        //CREATE the SocialPerformanceRecord object
        SocialPerformanceRecord record = new SocialPerformanceRecord(1,2, 3, 4,5,6,2028);

        // UPDATE the SalesMan by adding a PerformanceRecord
        controller.createSalesMan(salesMan);
        controller.addSocialPerformanceRecord(record,salesMan);

        // READ the Salesman
        SalesMan salesManObject = controller.readSalesMan(90448);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        // READ the SocialPerformanceRecord
        SocialPerformanceRecord recordObject = controller.readLastSocialPerformanceRecord(salesMan);
        System.out.println("Printing the object: " + "\n" + recordObject);

        System.out.println("___________________________________");

        // DELETE the SocialPerformanceRecord of salesMan
        controller.deleteByYearSocialPerformanceRecord(salesMan,2028);

        // Assertion that no SocialPerformanceRecord with year 2028 is in the collection
        assertEquals(controller.readByYearSocialPerformanceRecord(salesMan,2028),null);

        // Deletion
        controller.deleteAllSalesMan();
    }

    @Test
    void deleteLastSocialPerformanceRecord(){
        //CREATE the salesman object
        SalesMan salesMan = new SalesMan( "Leslie" , "Malton" , 90449);
        //CREATE the SocialPerformanceRecord object
        SocialPerformanceRecord record = new SocialPerformanceRecord(1,2, 3, 4,5,6,2028);
        SocialPerformanceRecord record2 = new SocialPerformanceRecord(1,2, 3, 4,5,6,2046);

        // UPDATE the SalesMan by adding a PerformanceRecord
        controller.createSalesMan(salesMan);
        controller.addSocialPerformanceRecord(record,salesMan);
        controller.addSocialPerformanceRecord(record2,salesMan);

        // READ the Salesman
        SalesMan salesManObject = controller.readSalesMan(90449);
        System.out.println("Printing the object: " + "\n" + salesManObject);

        // READ the SocialPerformanceRecord
        SocialPerformanceRecord recordObject = controller.readLastSocialPerformanceRecord(salesMan);
        System.out.println("Printing the object: " + "\n" + recordObject);

        System.out.println("___________________________________");

        // DELETE the SocialPerformanceRecord of salesMan
        controller.deleteLastSocialPerformanceRecord(salesMan);

        // Assertion that no SocialPerformanceRecord with year 2028 is in the collection
        Exception exception = assertThrows(Exception.class, () -> {
            controller.readByYearSocialPerformanceRecord(salesMan,2046);
        });

        // Deletion
        controller.deleteAllSalesMan();
    }

}