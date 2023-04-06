package com.example.mobile_labs_19522325;



public class PersonalSalary {
    String fullName;
    int grossSalary;

    public static final int dependenceCost = 11000000;

    public PersonalSalary(String fullName, int grossSalary) {
        this.fullName = fullName;
        this.grossSalary = grossSalary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(int grossSalary) {
        this.grossSalary = grossSalary;
    }
    double getSalary(){
        double tmp = this.grossSalary * (1 - 0.105);
        if(tmp <= dependenceCost)
            return tmp;
        return dependenceCost * (tmp - dependenceCost) * (1-0.05);
    }

}
