package com.mycompany.mavenproject1;
public class ToyotaCar extends Car
{
    ToyotaCar(int carId,String carModel,double carPrice)
    {
        super(carId, carModel, carPrice,"Toyota");
        brand="Toyota";
        this.resaleValue=getResaleValue(carPrice);
    }
    @Override
    final double getResaleValue(double carPrice)                       //get resale value of car
    {
        return carPrice*0.8;
    }
    @Override
    void displayCarDetails()                                          //display car details
    {
        System.out.println("car id: "+carId+"       "+"Car Model: "+carModel+"       "+"Car Price: "+carPrice+"        "+"Car resale value"+resaleValue);//+"\nlist of cars"+carsList);
    }
}