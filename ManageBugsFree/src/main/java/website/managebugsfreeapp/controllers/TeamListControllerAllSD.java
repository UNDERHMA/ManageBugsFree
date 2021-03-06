
package website.managebugsfreeapp.controllers;

import website.managebugsfreeapp.ejb.StatisticsEJB;
import website.managebugsfreeapp.ejb.TeamsEJB;
import website.managebugsfreeapp.pojos.StatisticSummaryBugReports;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.inject.Named;

/**
 *
 * @author mason
 */
@FacesConfig(
        // Activates CDI build-in beans
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // Tadas B. https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
        version = JSF_2_3
)

@Named
@RequestScoped
public class TeamListControllerAllSD {
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private StatisticsEJB statisticsEJB;
    private List<String> teams;
    private List<StatisticSummaryBugReports> statisticSummaryList = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        teams = teamsEJB.teamsByType("Software Development");
        for (int i = 0; i < teams.size(); i++) {
            StatisticSummaryBugReports statisticSummary = new StatisticSummaryBugReports();
            statisticSummary.setTitle(teams.get(i));
            statisticSummary.setStatusStatistics(statisticsEJB.getActiveBRs(teams.get(i)));
            statisticSummary.setCriticalStatusStatistics(statisticsEJB.getCriticalActiveBRs(teams.get(i)));
            statisticSummary.setBrsCompletedCurrentMonth(statisticsEJB.getBRsCompletedCurrentMonth(teams.get(i)));
            statisticSummary.setBrsCompletedYTD(statisticsEJB.getBRsCompletedYTD(teams.get(i)));
            statisticSummary.setAvgCompletionTimeCurrentMonth(statisticsEJB.getBRTimeCompletedCurrentMonth(teams.get(i)));
            statisticSummary.setAvgCompletionTimeYTD(statisticsEJB.getBRTimeCompletedYTD(teams.get(i)));
            statisticSummaryList.add(statisticSummary);
        }
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<StatisticSummaryBugReports> getStatisticSummaryList() {
        return statisticSummaryList;
    }

    public void setStatisticSummaryList(List<StatisticSummaryBugReports> statisticSummaryList) {
        this.statisticSummaryList = statisticSummaryList;
    }

}
