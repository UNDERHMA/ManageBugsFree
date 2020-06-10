/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.eclipselink.history;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;
/**
 *
 * @author mason
 */
 
// credit to Ben Gelernter and James from Eclipse https://wiki.eclipse.org/EclipseLink/Examples/JPA/History
public class LBTReportHistoryCustomizer implements DescriptorCustomizer {
 
    @Override
    public void customize(ClassDescriptor descriptor) {
        HistoryPolicy policy = new HistoryPolicy();
        policy.addHistoryTableName("LBTReportHistory");
        policy.addStartFieldName("start_date");
        policy.addEndFieldName("end_date");
        descriptor.setHistoryPolicy(policy);
    }
}
