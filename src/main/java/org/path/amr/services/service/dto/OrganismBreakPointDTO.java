package org.path.amr.services.service.dto;

public class OrganismBreakPointDTO {

    Long breakPointID;
    Long organismID;

    BreakpointDTO breakpoint;
    OrganismDTO organism;

    public Long getBreakPointID() {
        return breakPointID;
    }

    public void setBreakPointID(Long breakPointID) {
        this.breakPointID = breakPointID;
    }

    public Long getOrganismID() {
        return organismID;
    }

    public void setOrganismID(Long organismID) {
        this.organismID = organismID;
    }

    public BreakpointDTO getBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(BreakpointDTO breakpoint) {
        this.breakpoint = breakpoint;
    }

    public OrganismDTO getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismDTO organism) {
        this.organism = organism;
    }
}
