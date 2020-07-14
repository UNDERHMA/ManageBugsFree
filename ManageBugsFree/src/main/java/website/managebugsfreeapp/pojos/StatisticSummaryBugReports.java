
package website.managebugsfreeapp.pojos;

import website.managebugsfreeapp.entities.AggregateStatistics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mason
 */
public class StatisticSummaryBugReports extends StatisticSummary {
    
    private List<AggregateStatistics> statusStatistics = new ArrayList<>();
    private List<AggregateStatistics> criticalStatusStatistics = new ArrayList<>();
    private int brsCompletedCurrentMonth;
    private int brsCompletedYTD;

    public List<AggregateStatistics> getStatusStatistics() {
        return statusStatistics;
    }

    public void setStatusStatistics(List<AggregateStatistics> statusStatistics) {
        this.statusStatistics = statusStatistics;
    }

    public List<AggregateStatistics> getCriticalStatusStatistics() {
        return criticalStatusStatistics;
    }

    public void setCriticalStatusStatistics(List<AggregateStatistics> criticalStatusStatistics) {
        this.criticalStatusStatistics = criticalStatusStatistics;
    }

    
    public int getBrsCompletedCurrentMonth() {
        return brsCompletedCurrentMonth;
    }

    public void setBrsCompletedCurrentMonth(int brsCompletedCurrentMonth) {
        this.brsCompletedCurrentMonth = brsCompletedCurrentMonth;
    }

    public int getBrsCompletedYTD() {
        return brsCompletedYTD;
    }

    public void setBrsCompletedYTD(int brsCompletedYTD) {
        this.brsCompletedYTD = brsCompletedYTD;
    }
    
}
