/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAllSoftwareVersions", query = "SELECT software_version_description FROM SoftwareVersions ORDER BY software_version_id")
public class SoftwareVersions implements Serializable {

    private static final long serialVersionUID = 5L;
    
    @Column(name="software_version_id")
    @Id
    private Long softwareVersionId;
    
    @Column(name="software_version_description")
    @NotNull
    private String softwareVersionDescription;

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

    public String getSoftwareVersionDescription() {
        return softwareVersionDescription;
    }

    public void setSoftwareVersionDescription(String softwareVersionDescription) {
        this.softwareVersionDescription = softwareVersionDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (softwareVersionId != null ? softwareVersionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SoftwareVersions)) {
            return false;
        }
        SoftwareVersions other = (SoftwareVersions) object;
        if ((this.softwareVersionId == null && other.softwareVersionId != null) || (this.softwareVersionId != null && !this.softwareVersionId.equals(other.softwareVersionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.munderhill.entities.SoftwareVersions[ softwareVersionId=" + softwareVersionId + " ]";
    }
    
}
