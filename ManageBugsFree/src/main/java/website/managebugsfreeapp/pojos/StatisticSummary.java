
package website.managebugsfreeapp.pojos;

/**
 *
 * @author mason
 */
public class StatisticSummary {
    
    private String title = "";
    private String avgCompletionTimeCurrentMonth;
    private String avgCompletionTimeYTD;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvgCompletionTimeCurrentMonth() {
        return avgCompletionTimeCurrentMonth;
    }

    public void setAvgCompletionTimeCurrentMonth(String avgCompletionTimeCurrentMonth) {
        this.avgCompletionTimeCurrentMonth = avgCompletionTimeCurrentMonth;
    }

    public String getAvgCompletionTimeYTD() {
        return avgCompletionTimeYTD;
    }

    public void setAvgCompletionTimeYTD(String avgCompletionTimeYTD) {
        this.avgCompletionTimeYTD = avgCompletionTimeYTD;
    }
    
    
}


