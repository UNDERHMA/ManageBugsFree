/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


