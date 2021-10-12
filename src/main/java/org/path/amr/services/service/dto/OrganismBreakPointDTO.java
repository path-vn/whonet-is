package org.path.amr.services.service.dto;

public class OrganismBreakPointDTO {

    Long breakPointID;
    Long organismID;
    BreakpointDTO breakpointDTO;
    OrganismDTO organismDTO;

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

    public BreakpointDTO getBreakpointDTO() {
        return breakpointDTO;
    }

    public void setBreakpointDTO(BreakpointDTO breakpointDTO) {
        this.breakpointDTO = breakpointDTO;
    }

    public OrganismDTO getOrganismDTO() {
        return organismDTO;
    }

    public void setOrganismDTO(OrganismDTO organismDTO) {
        this.organismDTO = organismDTO;
    }
}
