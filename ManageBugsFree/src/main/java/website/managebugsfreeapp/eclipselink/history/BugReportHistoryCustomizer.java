/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.eclipselink.history;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;
 
// credit to Ben Gelernter and James from Eclipse https://wiki.eclipse.org/EclipseLink/Examples/JPA/History
// under license Eclipse Public License Version 2.0
public class BugReportHistoryCustomizer implements DescriptorCustomizer {
 
    @Override
    public void customize(ClassDescriptor descriptor) {
        HistoryPolicy policy = new HistoryPolicy();
        policy.addHistoryTableName("BugReportHistory");
        policy.addStartFieldName("start_date");
        policy.addEndFieldName("end_date");
        descriptor.setHistoryPolicy(policy);
    }
}
