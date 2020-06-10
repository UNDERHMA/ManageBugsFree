/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.pojos;

/**
 *
 * @author mason
 */
public class StatisiticSummaryBugReports {
    
    private int brsCompletedCurrentMonth;
    private int brsCompletedYTD;

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
