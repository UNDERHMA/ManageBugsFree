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
public class StatisticSummaryLBTReports extends StatisticSummary {
    
    private int activeLBTs;
    private int activeCriticalLBTs;
    private int lbtsCompletedCurrentMonth;
    private int lbtsCompletedYTD;

    public int getActiveLBTs() {
        return activeLBTs;
    }

    public void setActiveLBTs(int activeLBTs) {
        this.activeLBTs = activeLBTs;
    }

    public int getActiveCriticalLBTs() {
        return activeCriticalLBTs;
    }

    public void setActiveCriticalLBTs(int activeCriticalLBTs) {
        this.activeCriticalLBTs = activeCriticalLBTs;
    }
    
    public int getLbtsCompletedCurrentMonth() {
        return lbtsCompletedCurrentMonth;
    }

    public void setLbtsCompletedCurrentMonth(int lbtsCompletedCurrentMonth) {
        this.lbtsCompletedCurrentMonth = lbtsCompletedCurrentMonth;
    }

    public int getLbtsCompletedYTD() {
        return lbtsCompletedYTD;
    }

    public void setLbtsCompletedYTD(int lbtsCompletedYTD) {
        this.lbtsCompletedYTD = lbtsCompletedYTD;
    }

}
