/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.pagination;

import com.munderhill.entities.BugReport;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 * Code from Primefaces example https://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml
 * MIT License contained in this package folder src/main/webapp folder
 */
public class BugReportSorter implements Comparator<BugReport> {
    
    private String sortField;
     
    private SortOrder sortOrder;
     
    public BugReportSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @Override
    public int compare(BugReport bugReport1, BugReport bugReport2) {
        
           try {
                // extract data from fields based on sortField value
                Field field1;
                Field field2;
                if(this.sortField.equals("status") || this.sortField.equals("dateCreated")
                        || this.sortField.equals("dateFirstClosed")) {
                    field1 = BugReport.class.getSuperclass().getDeclaredField(this.sortField);
                    field2 = BugReport.class.getSuperclass().getDeclaredField(this.sortField);
                }
                else {
                    field1 = BugReport.class.getDeclaredField(this.sortField);
                    field2 = BugReport.class.getDeclaredField(this.sortField);
                }
                field1.setAccessible(true);
                field2.setAccessible(true);

                // Get and compare values
                int value;
                if(field1.getName().equals("dateCreated") || field1.getName().equals("dateFirstClosed")) {
                    Timestamp t1 = (Timestamp) field1.get(bugReport1);
                    Timestamp t2 = (Timestamp) field2.get(bugReport2);
                    if(t1 == null && t2 == null) {
                        value = 0;
                    }
                    else if(t1 == null && t2 != null) {
                        value = 1;
                    }
                    else if(t2 == null && t1!= null) {
                        value = -1;
                    }
                    else if (t1.after(t2)) {
                        value = 1;
                    }
                    else if (t2.after(t1)) {
                        value = -1;
                    }
                    else {
                        value = 0;
                    }
                }
                else {
                    Object value1 = field1.get(bugReport1);
                    Object value2 = field2.get(bugReport2);
                    value = ((Comparable)value1).compareTo(value2);
                }
                return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
            }
            catch(Exception e) {
                throw new RuntimeException();
            }
    }
}