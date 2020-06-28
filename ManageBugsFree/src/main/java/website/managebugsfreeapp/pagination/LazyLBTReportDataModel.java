/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.pagination;

import website.managebugsfreeapp.entities.LBTReport;
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
public class LazyLBTReportDataModel extends LazyDataModel<LBTReport> {
    
    public LazyLBTReportDataModel(List<LBTReport> data) {
        super();
        this.setWrappedData(data);
    }
    
    @Override
    public List<LBTReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<LBTReport> lbtReports = (List<LBTReport>) this.getWrappedData();
        
        // no filter needed for this application  
        
        // sort
        if (sortField != null && sortOrder != null) {
                Collections.sort(lbtReports, new LBTReportSorter(sortField, sortOrder));
        }
        
        //rowCount
        int dataSize = lbtReports.size();
        this.setRowCount(dataSize);
 
        //paginate
        if (dataSize > pageSize) {
            try {
                return lbtReports.subList(first, first + pageSize);
            }
            catch (IndexOutOfBoundsException e) {
                return lbtReports.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return lbtReports;
        }
    }
}
