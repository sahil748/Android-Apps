package com.mycompany.mavenproject1;
public class MarutiCar extends Car
{
    MarutiCar(int carId,String carModel,double carPrice)
    {
        super(carId, carModel, carPrice,"maruti");
        this.resaleValue=getResaleValue(carPrice);
    }
    @Override
    final double getResaleValue(double carPrice) //get resale value
    {
        return carPrice*0.6;
    }
    @Override
    void displayCarDetails()
    {
        System.out.println("car id: "+carId+"       "+"Car Model: "+carModel+"       "+"Car Price: "+carPrice+"        "+"Car resale value"+resaleValue);//+"\nlist of cars"+carsList);
    }
}
