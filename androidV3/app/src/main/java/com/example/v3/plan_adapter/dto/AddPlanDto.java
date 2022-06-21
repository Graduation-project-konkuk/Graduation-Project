package com.example.v3.plan_adapter.dto;

import java.io.Serializable;

public class AddPlanDto implements Serializable {

    private String saveWeight;
    private String saveExercise;
    private String saveReps;
    private String saveDate;

    public AddPlanDto(String saveWeight, String saveExercise, String saveReps, String saveDate) {
        this.saveWeight = saveWeight;
        this.saveExercise = saveExercise;
        this.saveReps = saveReps;
        this.saveDate = saveDate;
    }

    public void saveAll(String saveWeight, String saveExercise, String saveReps, String saveDate){
        this.saveWeight = saveWeight;
        this.saveExercise = saveExercise;
        this.saveReps = saveReps;
        this.saveDate = saveDate;
    }

    public String getSaveWeight() {
        return saveWeight;
    }

    public String getSaveExercise() {
        return saveExercise;
    }

    public String getSaveReps() {
        return saveReps;
    }

    public String getSaveDate() {
        return saveDate;
    }
}
