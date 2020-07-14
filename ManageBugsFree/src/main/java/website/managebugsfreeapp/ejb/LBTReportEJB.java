
package website.managebugsfreeapp.ejb;

import website.managebugsfreeapp.entities.BugReport;
import website.managebugsfreeapp.entities.BugReportLBTLink;
import website.managebugsfreeapp.entities.LBTReport;
import website.managebugsfreeapp.entities.LBTReportAdditionalNotes;
import website.managebugsfreeapp.entities.LBTReportHistory;
import website.managebugsfreeapp.entities.LBTSimilarBugReports;
import website.managebugsfreeapp.pojos.ChangeRecord;
import website.managebugsfreeapp.pojos.SearchFunctionLBTReport;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 *
 * @author mason
 */
@Stateless
public class LBTReportEJB {
    
    @PersistenceContext
    private EntityManager em;
    
    public LBTReport findLBTReportById(int lbtReportId){
        TypedQuery<LBTReport> query = em.createNamedQuery("findLBTReportById", LBTReport.class);
        query.setParameter("lbtReportId", lbtReportId);
        LBTReport result = query.getSingleResult();
        return result;
    }
    
    public List<ChangeRecord> findLBTReportChanges (int lbtReportId){
        TypedQuery<LBTReportHistory> query = em.createNamedQuery("findLBTReportChanges", LBTReportHistory.class);
        query.setParameter("lbtReportId", lbtReportId);
        List<LBTReportHistory> result = query.getResultList();
        List<ChangeRecord> changeRecordList = new ArrayList<>();
        if(!result.isEmpty()) {
            int i = 0;
            for(i = 0; i < result.size()-1; i++) {
                LBTReportHistory original = result.get(i);
                // CC BY-SA 4.0 License, available in package folder. Code snippet changed to fit functions needs.
                // credit to technique from Zim-Zam O'Pootertoot https://stackoverflow.com/questions/17095628/loop-over-all-fields-in-a-java-class
                Field[] originalArray = original.getClass().getDeclaredFields();
                // set fields to accessible
                for(Field f: originalArray) {
                    f.setAccessible(true);
                }
                LBTReportHistory afterChange = result.get(i+1);
                Field[] afterChangeArray = afterChange.getClass().getDeclaredFields();
                // set fields to accessible
                for(Field f: afterChangeArray) {
                    f.setAccessible(true);
                }
                for(int j = 0; j < originalArray.length; j++) {
                    Class type = originalArray[j].getType();
                    if(type.equals(Integer.class)) {
                        boolean valuesDiffer = false;
                        try {
                                 Integer int1 = (Integer) originalArray[j].get(original);
                                 Integer int2 = (Integer) afterChangeArray[j].get(afterChange);
                                 if(int1 != int2) {
                                     valuesDiffer = true;
                                 }
                                    } catch (Exception e) {
                                e.printStackTrace();
                                }
                        if(valuesDiffer) {
                            ChangeRecord changeRecord = new ChangeRecord();
                            changeRecord.setFieldChanged((String) originalArray[j].getName());
                            try {
                                // afterChangeArray[14] is the start_date column that indicates when a change took place
                                changeRecord.setUpdatedBy((String) afterChangeArray[10].get(afterChange));
                                changeRecord.setChangeDate((Timestamp) afterChangeArray[17].get(afterChange));
                                Integer contentsInteger = (Integer) originalArray[j].get(original);
                                Integer contents2Integer = (Integer) afterChangeArray[j].get(afterChange);
                                changeRecord.setOriginalValue(contentsInteger.toString());
                                changeRecord.setUpdatedValue(contents2Integer.toString());
                                changeRecordList.add(changeRecord);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if(type.equals(String.class)) {
                        boolean valuesDiffer = false;
                        try {
                                 String string1 = (String) originalArray[j].get(original);
                                 String string2 = (String) afterChangeArray[j].get(afterChange);
                                 if(!string1.equals(string2)) {
                                     String columnChanged = (String) originalArray[j].getName();
                                     if(!columnChanged.equals("updatedBy")) {
                                        valuesDiffer = true;
                                     }
                                 }
                                    } catch (Exception e) {
                                e.printStackTrace();
                                }
                        if(valuesDiffer) {
                            ChangeRecord changeRecord = new ChangeRecord();
                            changeRecord.setFieldChanged((String) originalArray[j].getName());
                            try {
                                // afterChangeArray[14] is the start_date column that indicates when a change took place
                                changeRecord.setUpdatedBy((String) afterChangeArray[10].get(afterChange));
                                changeRecord.setChangeDate((Timestamp) afterChangeArray[17].get(afterChange));
                                String contentsInteger = (String) originalArray[j].get(original);
                                String contents2Integer = (String) afterChangeArray[j].get(afterChange);
                                changeRecord.setOriginalValue(contentsInteger);
                                changeRecord.setUpdatedValue(contents2Integer);
                                changeRecordList.add(changeRecord);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }   
        return changeRecordList;
    }
    
    public List<LBTReport> queryLBTReports(SearchFunctionLBTReport searchFunction, boolean MyLBTReports){
            
            TypedQuery<LBTReport> query;
            if(MyLBTReports) {
                query = em.createNamedQuery("queryMyLBTReports", LBTReport.class);
            }
            else {
                query = em.createNamedQuery("queryLBTReports", LBTReport.class);
            }
            /* Setting query parameters. If they are empty, we set the parameter to 1=1 
                so that empty parameters don't invalidate searches */
            if(searchFunction.getLbtReportId() == null) {
                query.setParameter("lbtReportId", 0);
            }
            else {
                query.setParameter("lbtReportId", searchFunction.getLbtReportId());
            }

            if(!searchFunction.getTitle().equals("")) {
                query.setParameter("title", "%" + searchFunction.getTitle() + "%");
            }
            else {
                query.setParameter("title", "%");
            }
            
            if(!searchFunction.getTopic().equals("") && !searchFunction.getTopic().equals("-----")) {
                query.setParameter("topic", "%" + searchFunction.getTopic() + "%");
            }
            else {
                query.setParameter("topic", "%");
            }

            if(searchFunction.getBugReportId() == null) {
                query.setParameter("linkedBugReportId", -1);
            }
            else {
                query.setParameter("linkedBugReportId", searchFunction.getBugReportId());
            }

            if(searchFunction.getDevelopmentTeam().length() != 0 && !searchFunction.getDevelopmentTeam().equals("-----")) {
                query.setParameter("developmentTeam", searchFunction.getDevelopmentTeam());
            }
            else {
                query.setParameter("developmentTeam", "%");
            }

            if(searchFunction.getCustomerSupportTeam().length() != 0 && !searchFunction.getCustomerSupportTeam().equals("-----")) {
                query.setParameter("customerSupportTeam", searchFunction.getCustomerSupportTeam());
            }
            else {
                query.setParameter("customerSupportTeam", "%");
            }
            
            if (!searchFunction.getUserAssigned().equals("-----"))  {
                query.setParameter("userAssigned",  "%" + searchFunction.getUserAssigned() + "%");
            }
            else {
                query.setParameter("userAssigned",  "%");
            }
            
            if(!searchFunction.getUserSubmitted().equals("-----")) {
                query.setParameter("userSubmitted",  "%" + searchFunction.getUserSubmitted() + "%");
            }
            else {
                query.setParameter("userSubmitted", "%");
            }
                
            if(searchFunction.getSeverityEstimate().length() != 0 && !searchFunction.getSeverityEstimate().equals("-----")) {
                query.setParameter("severityEstimate", searchFunction.getSeverityEstimate());
            }
            else {
                query.setParameter("severityEstimate", "%");
            }
            
            if(searchFunction.getStartDate() != null) {
                query.setParameter("startdate", 
                        searchFunction.dateConverter(searchFunction.getStartDate()));
            }
            else {
                query.setParameter("startdate", new java.sql.Date(0)); //set to 1/1/1970 if no date entered
            }
            
            if(searchFunction.getEndDate() != null) {
            query.setParameter("enddate", 
                        searchFunction.dateConverter(searchFunction.getEndDate()));
            }
            else {
                query.setParameter("enddate", new java.sql.Date(7258118400000L)); //set to 1/1/2200 if no date entered
            }

            if(searchFunction.getStartDateAllBRsClosed() != null) {
                query.setParameter("startdateallbrsclosed", 
                        searchFunction.dateConverter(searchFunction.getStartDateAllBRsClosed()));
            }
            else {
                query.setParameter("startdateallbrsclosed", new java.sql.Date(0)); //set to 1/1/1970 if no date entered
            }
            
            if(searchFunction.getEndDateAllBRsClosed() != null) {
            query.setParameter("enddateallbrsclosed", 
                    searchFunction.dateConverter(searchFunction.getEndDateAllBRsClosed()));
            }
            else {
                query.setParameter("enddateallbrsclosed", new java.sql.Date(7258118400000L)); //set to 1/1/2200 if no date entered
            }
            
            /* sets search query variable = 0 so results with null dates are returned when not searching by
                startdateallbrsclosed or enddateallbrsclosed */
            if(searchFunction.getStartDateAllBRsClosed() == null && searchFunction.getEndDateAllBRsClosed() == null) {
                query.setParameter("search", 0);
            }
            else {
                query.setParameter("search", 1);
            }
            
            if(searchFunction.isActiveReports()) {
                //get only non-closed reports if isActiveReports is selected
                query.setParameter("search", 0);
                query.setParameter("enddateallbrsclosed", new java.sql.Date(0)); //set to 1/1/1970
                query.setParameter("startdateallbrsclosed", new java.sql.Date(0)); //set to 1/1/1970
            }
            
            if(searchFunction.getSoftwareVersion().length() != 0 && !searchFunction.getSoftwareVersion().equals("-----")) {
                query.setParameter("softwareVersion", searchFunction.getSoftwareVersion());
            }
            else {
                query.setParameter("softwareVersion", "%");
            }

            if(searchFunction.getSimilarBugReportId() == null) {
                query.setParameter("similarBugReportId", -1);
            }
            else {
                query.setParameter("similarBugReportId", searchFunction.getSimilarBugReportId());
            }

            if(!searchFunction.getAbbreviatedStackTrace().equals("")) {
                query.setParameter("abbreviatedStackTrace", "%" + searchFunction.getAbbreviatedStackTrace() + "%");
            }
            else {
                query.setParameter("abbreviatedStackTrace", "%");
            }

            if(!searchFunction.getBugDescription().equals("")) {
                query.setParameter("bugDescription", "%" + searchFunction.getBugDescription() + "%");
            }
            else {
                query.setParameter("bugDescription", "%");
            }
            
        List<LBTReport> result = query.getResultList();
        return result;
    }
    
    public void createLBTReport(LBTReport lbtreport){
         em.persist(lbtreport); 
    }
    
    public void updateLBTReport(LBTReport lbtReport, LBTReportAdditionalNotes note,
                              List<LBTSimilarBugReports> similarSelectedValues, 
                              List<BugReportLBTLink> linkedSelectedValues) {
        // remove any similarbugreportids marked for deletion on LBTReport.xhtml
        if(!similarSelectedValues.isEmpty()) {
            for(LBTSimilarBugReports s1 : similarSelectedValues) {
                Iterator<LBTSimilarBugReports> s2 = lbtReport.getSimilarBugReportIds().iterator();
                while(s2.hasNext()) {
                    if(s2.next().equals(s1)) {
                        s2.remove();
                    }
                }
            }
        }
        // remove any linkedbugreportids marked for deletion on LBTReport.xhtml
        if(!linkedSelectedValues.isEmpty()) {
            for(BugReportLBTLink b1 : linkedSelectedValues) {
                Iterator<BugReportLBTLink> b2 = lbtReport.getLinkedBugReportIds().iterator();
                while(b2.hasNext()) {
                    if(b2.next().equals(b1)) {
                        b2.remove();
                    }
                }
            }
        }
        // merge updated lbtreport and persist new note
        em.merge(lbtReport);
        if(note.getNote() != null && note.getNote().length() > 0) {
        em.persist(note);
        }
    }
    
     public boolean validateLinkedBRId(Integer i) {
        TypedQuery<BugReport> query = em.createNamedQuery("findBugReportById", BugReport.class);
        query.setParameter("bugReportId", i);
        
        List<BugReport> result = query.getResultList();
        if(result.isEmpty()) {
            return false;
        }
        return true;
    }   
    
    public boolean validateSimilarBRId(Integer i) {
        TypedQuery<BugReport> query = em.createNamedQuery("findBugReportById", BugReport.class);
        query.setParameter("bugReportId", i);
        
        List<BugReport> result = query.getResultList();
        if(result.isEmpty()) {
            return false;
        }
        return true;
    }   
    
    public List<LBTReportAdditionalNotes> notesList(int lbtReportId){
        TypedQuery<LBTReportAdditionalNotes> query = em.createNamedQuery("findAllNotesByLBTId", LBTReportAdditionalNotes.class);
        query.setParameter("lbtReportId", lbtReportId);
        List<LBTReportAdditionalNotes> result = query.getResultList();
        return result;
    }
     
    public List<String> prioritiesList(){
        TypedQuery<String> query = em.createNamedQuery("findAllPriorities", String.class);
        return query.getResultList();
    }
    
    public List<String> softwareVersionsList(){
        TypedQuery<String> query = em.createNamedQuery("findAllSoftwareVersions", String.class);
        return query.getResultList();
    }
    
    public List<String> statusesList(){
        TypedQuery<String> query = em.createNamedQuery("findAllStatuses", String.class);
        return query.getResultList();
    }
    
    public List<String> topicsList(){
        TypedQuery<String> query = em.createNamedQuery("findAllTopics", String.class);
        return query.getResultList();
    }

}
