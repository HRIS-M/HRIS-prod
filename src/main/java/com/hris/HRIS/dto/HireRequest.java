package com.hris.HRIS.dto;

import java.util.List;

public class HireRequest {

    private List<String> candidateIds;

    public List<String> getCandidateIds() {
        return candidateIds;
    }

    public void setCandidateIds(List<String> candidateIds) {
        this.candidateIds = candidateIds;
    }
}
