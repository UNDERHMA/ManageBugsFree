/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.pagination;

import com.munderhill.entities.BugReport;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Code from Primefaces example https://www.primefaces.org/showcase/ui/data/datatable/lazy.xhtml
 * MIT License contained in the src/main/webapp folder
 */
public class LazyBugReportDataModel extends LazyDataModel<BugReport> {
    
    public LazyBugReportDataModel(List<BugReport> data) {
        super();
        this.setWrappedData(data);
    }
    
    @Override
    public List<BugReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<BugReport> bugReports = (List<BugReport>) this.getWrappedData();
        
        // no filter needed for this application  
        
        // sort
        if (sortField != null && sortOrder != null) {
                Collections.sort(bugReports, new BugReportSorter(sortField, sortOrder));
        }
        
        //rowCount
        int dataSize = bugReports.size();
        this.setRowCount(dataSize);
 
        //paginate
        if (dataSize > pageSize) {
            try {
                return bugReports.subList(first, first + pageSize);
            }
            catch (IndexOutOfBoundsException e) {
                return bugReports.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return bugReports;
        }
    }
}
