package com.example.v3.plan_adapter;

public class PlanItem {

    String date;
    String weight;
    String exercise;
    String reps;

    public PlanItem(String date, String weight, String exercise, String reps) {
        this.weight = weight;
        this.exercise = exercise;
        this.reps = reps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }
}
