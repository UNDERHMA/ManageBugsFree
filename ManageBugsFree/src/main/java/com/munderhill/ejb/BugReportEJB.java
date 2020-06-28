/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.ejb;

import com.munderhill.entities.BRSimilarBugReports;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.munderhill.entities.BugReport;
import com.munderhill.entities.BugReportAdditionalNotes;
import com.munderhill.entities.BugReportHistory;
import com.munderhill.entities.BugReportLBTLink;
import com.munderhill.entities.LBTReport;
import com.munderhill.pojos.ChangeRecord;
import com.munderhill.pojos.SearchFunctionBugReport;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;



/**
 *
 * @author mason
 */

@Stateless
public class BugReportEJB {
        
    @PersistenceContext
    private EntityManager em;
    
    public boolean AllLinkedBugReportsClosed(int lbtReportId, int bugReportId){
        Query query = em.createNamedQuery("AnyLinkedBugReportsNotClosed");
        query.setParameter("lbtReportId", lbtReportId);
        query.setParameter("bugReportId", bugReportId);
        Long result = (Long) query.getSingleResult();
        if(result == 0) {
            return true;
        }
        return false;
    }
    
    public BugReport findBugReportById(int bugReportId){
        TypedQuery<BugReport> query = em.createNamedQuery("findBugReportById", BugReport.class);
        query.setParameter("bugReportId", bugReportId);
        BugReport result = query.getSingleResult();
        return result;
    }
    
    public List<ChangeRecord> findBugReportChanges (int bugReportId){
        TypedQuery<BugReportHistory> query = em.createNamedQuery("findBugReportChanges", BugReportHistory.class);
        query.setParameter("bugReportId", bugReportId);
        List<BugReportHistory> result = query.getResultList();
        List<ChangeRecord> changeRecordList = new ArrayList<>();
        if(!result.isEmpty()) {
            for(int i = 0; i < result.size()-1; i++) {
                BugReportHistory original = result.get(i);
                // credit to technique from Zim-Zam O'Pootertoot https://stackoverflow.com/questions/17095628/loop-over-all-fields-in-a-java-class
                Field[] originalArray = original.getClass().getDeclaredFields();
                // set fields to accessible
                for(Field f: originalArray) {
                    f.setAccessible(true);
                }
                BugReportHistory afterChange = result.get(i+1);
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
                                changeRecord.setUpdatedBy((String) afterChangeArray[9].get(afterChange));
                                changeRecord.setChangeDate((Timestamp) afterChangeArray[16].get(afterChange));
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
                                changeRecord.setUpdatedBy((String) afterChangeArray[9].get(afterChange));
                                changeRecord.setChangeDate((Timestamp) afterChangeArray[16].get(afterChange));
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
    
    public List<BugReport> queryBugReports(SearchFunctionBugReport searchFunction, boolean MyBugReports){
            
            TypedQuery<BugReport> query;
            if(MyBugReports) {
                query = em.createNamedQuery("queryMyBugReports", BugReport.class);
            }
            else {
                query = em.createNamedQuery("queryBugReports", BugReport.class);
            }
            /* Setting query parameters. If they are empty, we set the parameter to 1=1 
                so that empty parameters don't invalidate searches */
            if(searchFunction.getBugReportId() == null) {
                query.setParameter("bugReportId", 0);
            }
            else {
                query.setParameter("bugReportId", searchFunction.getBugReportId());
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

            if(searchFunction.getLbtReportId() == null) {
                query.setParameter("lbtReportId", -1);
            }
            else {
                query.setParameter("lbtReportId", searchFunction.getLbtReportId());
            }

            if(searchFunction.getDevelopmentTeam().length() != 0 && !searchFunction.getDevelopmentTeam().equals("-----")) {
                query.setParameter("teamName", searchFunction.getDevelopmentTeam());
            }
            else {
                query.setParameter("teamName", "%");
            }

            if (!searchFunction.getUserAssigned().equals("-----"))  {
                query.setParameter("userAssigned",  "%" + searchFunction.getUserAssigned() + "%");
            }
            else {
                query.setParameter("userAssigned",  "%");
            }
            
            if (!searchFunction.getOpenedBy().equals("-----"))  {
                query.setParameter("openedBy",  "%" + searchFunction.getOpenedBy() + "%");
            }
            else {
                query.setParameter("openedBy",  "%");
            }
            
            if (searchFunction.getPriority().length() != 0 && !searchFunction.getPriority().equals("-----")) {
                query.setParameter("priority", searchFunction.getPriority());
            }
            else {
                query.setParameter("priority",  "%");
            }

            if(searchFunction.getStatus().length() != 0 && !searchFunction.getStatus().equals("-----")) {
                query.setParameter("status", searchFunction.getStatus());
            }
            else {
                query.setParameter("status", "%");
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
            
            if(searchFunction.getStartDateClosed() != null) {
                query.setParameter("startdateclosed", 
                        searchFunction.dateConverter(searchFunction.getStartDateClosed()));
            }
            else {
                query.setParameter("startdateclosed", new java.sql.Date(0)); //set to 1/1/1970 if no date entered
            }
            
            if(searchFunction.getEndDateClosed() != null) {
            query.setParameter("enddateclosed", 
                    searchFunction.dateConverter(searchFunction.getEndDateClosed()));
            }
            else {
                query.setParameter("enddateclosed", new java.sql.Date(7258118400000L)); //set to 1/1/2200 if no date entered
            }
            
            /* sets search query variable = 0 so results with null dates are returned when not searching by
                startdateclosed or enddateclosed */
            if(searchFunction.getStartDateClosed() == null && searchFunction.getEndDateClosed() == null) {
                query.setParameter("search", 0);
            }
            else {
                query.setParameter("search", 1);
            }
            
            if(searchFunction.isActiveReports()) {
                //get only non-closed reports if isActiveReports is selected
                query.setParameter("search", 0);
                query.setParameter("enddateclosed", new java.sql.Date(0)); //set to 1/1/1970
                query.setParameter("startdateclosed", new java.sql.Date(0)); //set to 1/1/1970
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
            
        List<BugReport> result = query.getResultList();
        return result;
    }
    
    public void createBugReport(BugReport bugreport){
         em.persist(bugreport);
    }
    
    public void updateBugReport(BugReport bugReport, BugReportAdditionalNotes note,
                                List<BRSimilarBugReports> similarSelectedValues,
                                List<BugReportLBTLink> linkedSelectedValues) {
        // remove any similarbugreportids marked for deletion on BugReport.xhtml
        if(!similarSelectedValues.isEmpty()) {
            for(BRSimilarBugReports s1 : similarSelectedValues) {
                Iterator<BRSimilarBugReports> s2 = bugReport.getSimilarBugReportIds().iterator();
                while(s2.hasNext()) {
                    if(s2.next().equals(s1)) {
                        s2.remove();
                    }
                }
            }
        }
        // remove any linkedlbtreportids marked for deletion on BugReport.xhtml
        if(!linkedSelectedValues.isEmpty()) {
            for(BugReportLBTLink b1 : linkedSelectedValues) {
                Iterator<BugReportLBTLink> b2 = bugReport.getLinkedLbtReportIds().iterator();
                while(b2.hasNext()) {
                    if(b2.next().equals(b1)) {
                        b2.remove();
                    }
                }
            }
        }
        // merge updated lbtreport and persist new note
        em.merge(bugReport);
        if(note.getNote() != null && note.getNote().length() > 0) {
        em.persist(note);
        }
    }
    
    public boolean validateLinkedLBTId(Integer i) {
        TypedQuery<LBTReport> query = em.createNamedQuery("findLBTReportById", LBTReport.class);
        query.setParameter("lbtReportId", i);
        
        List<LBTReport> result = query.getResultList();
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
    
    public List<BugReportAdditionalNotes> notesList(int bugReportId){
        TypedQuery<BugReportAdditionalNotes> query = em.createNamedQuery("findAllNotesByBRId", BugReportAdditionalNotes.class);
        query.setParameter("bugReportId", bugReportId);
        List<BugReportAdditionalNotes> result = query.getResultList();
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
