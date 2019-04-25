package com.mycompany.mavenproject1;

public abstract class Car 
{
    String brand;
    int carId;
    String carModel;
    double carPrice;
    double resaleValue;
    Car(int  carId,String carModel,double Price, String brand)
    {
        this.carId=carId;
        this.carModel=carModel;
        this.carPrice=Price;
        this.brand=brand;
    }
    abstract double getResaleValue(double carPrice);   //get resale value

    void displayCarDetails() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
