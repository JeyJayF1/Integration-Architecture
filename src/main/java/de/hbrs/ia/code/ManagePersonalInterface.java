package de.hbrs.ia.code;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;

import java.util.List;

/**
 * Code lines are commented for suppressing compile errors.
 * Are there any CRUD-operations missing?
 */
public interface ManagePersonalInterface {
    public void createSalesMan(SalesMan record);
    public void deleteSalesMan(int sid);
    public SalesMan readSalesMan(int sid);

    public void deleteAllSalesMan();
    public List<SalesMan> readAllSalesMen();

    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan);
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan);
    public SocialPerformanceRecord readLastSocialPerformanceRecord(SalesMan salesmMan);
    public SocialPerformanceRecord readByYearSocialPerformanceRecord(SalesMan salesMan, int year);
    public void deleteByYearSocialPerformanceRecord(SalesMan salesMan, int year);
    public void deleteLastSocialPerformanceRecord(SalesMan salesMan);
    //Remark: an SocialPerformanceRecord corresponds to part B of a bonus sheet



}
