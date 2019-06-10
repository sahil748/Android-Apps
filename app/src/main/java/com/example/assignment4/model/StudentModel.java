package com.example.assignment4.model;

public class StudentModel {
        private String name,roll,className;
        public StudentModel(String name,String roll,String className){
            this.name=name;
            this.roll=roll;
            this.className=className;
        }
        public String getName(){
            return name;
        }
        public String getRoll()
        {
            return roll;
        }
        public String getClassName()
        {
            return className;
        }
    }


