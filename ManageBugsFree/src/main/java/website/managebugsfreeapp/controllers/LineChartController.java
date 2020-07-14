
package website.managebugsfreeapp.controllers;

import website.managebugsfreeapp.ejb.LineChartEJB;
import website.managebugsfreeapp.entities.LineChartData;
import website.managebugsfreeapp.entities.LineChartDataWithInterval;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
Drawn from Primefaces example https://www.primefaces.org/showcase/ui/chart/line.xhtml
MIT License contained in this package folder
 */
@FacesConfig(
        // Activates CDI build-in beans
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // Tadas B. https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
        version = JSF_2_3
)

@Named
@RequestScoped
public class LineChartController implements Serializable {
    
    // see author information at top of class for Primefaces reference. Primefaces examples were used for this.
    @EJB
    private LineChartEJB lineChartEJB;
    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private LineChartModel lineModel3;
    private List<LineChartData> lineChartData1 = new ArrayList<>();
    private List<LineChartData> lineChartData2 = new ArrayList<>();
    private List<LineChartData> lineChartData3 = new ArrayList<>();
    private List<LineChartDataWithInterval> lineChartData4 = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        // Get chart data from EJB
        lineChartData1 = lineChartEJB.getBugsOpenedByDay();
        lineChartData2 = lineChartEJB.getBugsClosedByDay();
        lineChartData3 = lineChartEJB.getBugsOutstandingOverTime();
        lineChartData4 = lineChartEJB.getAvgDaysToClosedOverTime();
        
        // Create Models
         createLineModels();
    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }

    public LineChartModel getLineModel3() {
        return lineModel3;
    }

    public void setLineModel3(LineChartModel lineModel3) {
        this.lineModel3 = lineModel3;
    }

    public List<LineChartData> getLineChartData1() {
        return lineChartData1;
    }

    public void setLineChartData1(List<LineChartData> lineChartData1) {
        this.lineChartData1 = lineChartData1;
    }

    public List<LineChartData> getLineChartData2() {
        return lineChartData2;
    }

    public void setLineChartData2(List<LineChartData> lineChartData2) {
        this.lineChartData2 = lineChartData2;
    }

    public List<LineChartData> getLineChartData3() {
        return lineChartData3;
    }

    public void setLineChartData3(List<LineChartData> lineChartData3) {
        this.lineChartData3 = lineChartData3;
    }

    public List<LineChartDataWithInterval> getLineChartData4() {
        return lineChartData4;
    }

    public void setLineChartData4(List<LineChartDataWithInterval> lineChartData4) {
        this.lineChartData4 = lineChartData4;
    }

    private LineChartModel initModel1() {
        LineChartModel model = new LineChartModel();
        
        ChartSeries opened = new ChartSeries();
        opened.setLabel("BRs Opened");
        for(LineChartData l : lineChartData1) {
            opened.set(l.getYearMonth(), l.getCount());
        }
        
        ChartSeries closed = new ChartSeries();
        closed.setLabel("BRs Closed");
        for(LineChartData l : lineChartData2) {
            closed.set(l.getYearMonth(), l.getCount());
        }
        
        model.addSeries(opened);
        model.addSeries(closed);
        return model;
    }
    
    private LineChartModel initModel2() {
        LineChartModel model = new LineChartModel();
        
        ChartSeries outstanding = new ChartSeries();
        outstanding.setLabel("BRs Outstanding");
        for(LineChartData l : lineChartData3) {
            outstanding.set(l.getYearMonth(), l.getCount());
        }
        
        model.addSeries(outstanding);
        return model;
    }
    
    private LineChartModel initModel3() {
        LineChartModel model = new LineChartModel();
        
        ChartSeries avgTimeToCompletion = new ChartSeries();
        avgTimeToCompletion.setLabel("Avg. Days to Close BRs");
        for(LineChartDataWithInterval l : lineChartData4) {
            avgTimeToCompletion.set(l.getYearMonth(), l.getTimeInterval());
        }
        
        model.addSeries(avgTimeToCompletion);
        return model;
    }
    
    private void createLineModels() {
        lineModel1 = initModel1();
        lineModel2 = initModel2();
        lineModel3 = initModel3();
        
        lineModel1.setTitle("Bugs Opened vs Bugs Closed Per Month");
        lineModel2.setTitle("Avg. Bugs Outstanding Per Month");
        lineModel3.setTitle("Avg. Days to Close BRs Per Month");

        lineModel1.setLegendPosition("e");
        lineModel2.setLegendPosition("e");
        lineModel3.setLegendPosition("e");

        Axis yAxis1 = lineModel1.getAxis(AxisType.Y);
        Axis yAxis2 = lineModel2.getAxis(AxisType.Y);
        Axis yAxis3 = lineModel3.getAxis(AxisType.Y);
        yAxis1.setMin(0);
        yAxis1.setMax(15);
        yAxis2.setMin(0);
        yAxis2.setMax(25);
        yAxis3.setMin(0);
        yAxis3.setMax(25);
        
        lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Months"));
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Months"));
        lineModel3.getAxes().put(AxisType.X, new CategoryAxis("Months"));
        
        Axis xAxis1 = lineModel1.getAxis(AxisType.X);
        Axis xAxis2 = lineModel2.getAxis(AxisType.X);
        Axis xAxis3 = lineModel3.getAxis(AxisType.X);
        xAxis1.setTickAngle(-50);
        xAxis2.setTickAngle(-50);
        xAxis3.setTickAngle(-50);
    }

}
