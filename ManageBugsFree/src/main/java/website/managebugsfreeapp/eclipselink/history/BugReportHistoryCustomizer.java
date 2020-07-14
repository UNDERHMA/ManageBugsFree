
package website.managebugsfreeapp.eclipselink.history;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

// Eclipse Public License Version 2.0 in package folder
// credit to Ben Gelernter and James from Eclipse https://wiki.eclipse.org/EclipseLink/Examples/JPA/History

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
