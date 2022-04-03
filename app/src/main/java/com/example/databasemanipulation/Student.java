package com.example.databasemanipulation;

public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private String marks;
    private String course;
    private String credits;

    public Student(Integer id, String firstName, String lastName, String marks, String course, String credits){
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMarks(marks);
        this.setCourse(course);
        this.setCredits(credits);
    }

    public Student(){

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
