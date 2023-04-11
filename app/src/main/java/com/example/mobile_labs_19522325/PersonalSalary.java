package com.example.mobile_labs_19522325;



public class PersonalSalary {
    String fullName;
    double grossSalary;

    public static final int dependenceCost = 11000000;

    public PersonalSalary(){}
    public PersonalSalary(String fullName, double grossSalary) {
        this.fullName = fullName;
        this.grossSalary = grossSalary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary)
    {
        this.grossSalary = grossSalary;
    }
    public String toString(){
        return this.fullName + " - " + " Net Salary: " + this.grossSalary;
    }

}
