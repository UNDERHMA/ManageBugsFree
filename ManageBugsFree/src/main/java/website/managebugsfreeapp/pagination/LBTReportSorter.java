/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.pagination;

import website.managebugsfreeapp.entities.LBTReport;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 * Code from Primefaces example https://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml
 * MIT License contained in this package folder src/main/webapp folder
 */
public class LBTReportSorter implements Comparator<LBTReport> {
    
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LBTReportSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @Override
    public int compare(LBTReport lbtReport1, LBTReport lbtReport2) {
        
           try {
                // extract data from fields based on sortField value
                Field field1;
                Field field2;
                if(this.sortField.equals("dateCreated") || this.sortField.equals("dateAllLinkedBRsClosed")) {
                    field1 = LBTReport.class.getSuperclass().getDeclaredField(this.sortField);
                    field2 = LBTReport.class.getSuperclass().getDeclaredField(this.sortField);
                }
                else {
                    field1 = LBTReport.class.getDeclaredField(this.sortField);
                    field2 = LBTReport.class.getDeclaredField(this.sortField);
                }
                field1.setAccessible(true);
                field2.setAccessible(true);

                // Get and compare values
                int value;
                if(field1.getName().equals("dateCreated") || field1.getName().equals("dateAllLinkedBRsClosed")) {
                    Timestamp t1 = (Timestamp) field1.get(lbtReport1);
                    Timestamp t2 = (Timestamp) field1.get(lbtReport2);
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
                    Object value1 = field1.get(lbtReport1);
                    Object value2 = field2.get(lbtReport2);
                    value = ((Comparable)value1).compareTo(value2);
                }
                return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
            }
            catch(Exception e) {
                throw new RuntimeException();
            }
    }
}