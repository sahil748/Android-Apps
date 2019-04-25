
package com.mycompany.mavenproject1;
import java.util.*;
public class Customer 
{
    Scanner sc=new Scanner(System.in);
    private final int id;
    private final String name;
    ArrayList<Car> carsList=new ArrayList<>();
    Customer(int id,String name,Car carInfo)
    {
        this.id=id;
        this.name=name;
        carsList.add(carInfo);
    }
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public void displayCustomerDetails()                                         //display details of customer
    {
        System.out.println("customer id: "+id+"       "+"Customer name: "+name+"\nCar details: ");
                displayCarsList();
    }
    void displayCarsList()                                                //display car details
    {
        int size = carsList.size();
        for (int i = 0; i < size; i++) 
        { 
            Car car = carsList.get(i);
            System.out.println("car id : "+car.carId+"        "+"car brand : "+car.brand+"       "
                               +"car model: "+car.carModel+"    "+"car price : "+car.carPrice +"         car resale value: " +car.resaleValue); 
        } 
    }


}
