package com.mycompany.mavenproject1;
import java.util.*;
import java.util.Random;
import java.util.stream.*; 
import java.util.Collections;
import java.util.Comparator;

public class Admin 
{
    static Scanner sc =new Scanner(System.in);
    static Map<Integer,Customer> CustomersDetails=new TreeMap<>();//(new SortMap());
    static HashMap<String,Car>MarutiCars =new HashMap<>();
    static HashMap<String,Car>ToyotaCars =new HashMap<>();
    static HashMap<String,Car>HyundaiCars =new HashMap<>();
    static List<Integer> adminSelected=new ArrayList<>();
    static List<Integer> randomSelected=new ArrayList<>();
    
    
    
    static int customer_Id=0;
    static void printSortedDetails()
    {
        List Listofvalues= CustomersDetails.values().stream().           //get details of customers from map to arraylist
        collect(Collectors.toCollection(ArrayList::new));                
        Collections.sort(Listofvalues, new Comparator<Customer>()       //sorting customers according to names using comparator
        {
            @Override
            public int compare(Customer c1,Customer c2)
            {
                return String.valueOf(c1.getName()).compareTo(c2.getName());
                
            }
        });
        for (Iterator it = Listofvalues.iterator(); it.hasNext();)       //printing details
        {
            Customer str = (Customer) it.next();
            str.displayCustomerDetails();
        }
        
    } 
       static int getInput( )                                     //validating integer input
   {
   Scanner sc =new Scanner(System.in);
   String input=sc.nextLine();
   int integer;
                    try
                    {
                        integer=Integer.parseInt(input);
                    }
                    catch (Exception exception)
                    {
                        System.out.println("Please Enter Integer.");
                        integer=getInput();
                    }
       return integer;
   }
   static String displayAdminFunctions()                          //show menu of admin and return the choice of admin
    {
        printStar();
        System.out.println("Enter 1 to add new customer\n"
            + "Enter 2 to add new car for existing customer\n"
            +"Enter 3 to list all customer with their cars\n"
            + "Enter 4 to see details of  customer\nEnter 5 to generate Prizes\nEnter 6 to exit");
        printStar(); 
        String choice=null;   
       while(choice==null)
       {
           choice=sc.nextLine();
           if(!(choice.equals("1")||choice.equals("2")||choice.equals("3")||choice.equals("4")||choice.equals("5")||choice.equals("6")))  //validating choice of admin
            {
                System.out.println("invalid input.Please enter again");
                choice=null;
            }
       }
       return choice;
    }
   static void marutiCars(int carId,String carModel,double carPrice) //add new car model to their 
    {
        Car marutiCar=new MarutiCar(carId,carModel,carPrice);
        MarutiCars.put(carModel,marutiCar);
    }
   static void toyotaCars(int carId,String carModel,double carPrice) //respective brands
    {
        Car toyotaCar=new ToyotaCar(carId,carModel,carPrice);
        ToyotaCars.put(carModel,toyotaCar);
    }
   static void hyundaiCars(int carId,String carModel,double carPrice)
    {
        Car hyundaiCar=new HyundaiCar(carId,carModel,carPrice);
        HyundaiCars.put(carModel,hyundaiCar);
    }
   static void displayMarutiCars()                            //shows list of maruti cars available
   {
       Set<Map.Entry<String,Car>> entries= MarutiCars.entrySet();
                   entries.stream().map((cars) -> cars.getValue()).forEachOrdered((carr) -> {
                       carr.displayCarDetails();});
   }
    static void displayToyotaCars()                           //shows list of toyota cars available
   {
       Set<Map.Entry<String,Car>> entries= ToyotaCars.entrySet();
                   entries.stream().map((cars) -> cars.getValue()).forEachOrdered(Car::displayCarDetails);
   }
    static void displayHyundaiCars()                          // shows list of Hyundai cars available
   {
       Set<Map.Entry<String,Car>> entries= HyundaiCars.entrySet();
                   entries.stream().map((cars) -> cars.getValue()).forEachOrdered((var carr) -> {
                       carr.displayCarDetails();});
   }
    static Car addCar()                                       //add car to customer's carlist
    {
        Car carInfo=null;
        String carModel;
        String carBrand=null;
        while(carBrand==null)
        {
            System.out.println("Enter car brand");
            carBrand=sc.nextLine();
            if(carBrand.equalsIgnoreCase("maruti"))
            {
                printStar();
                displayMarutiCars();
                printStar();
                while(carInfo==null)
                {
                    System.out.println("Enter car model");
                    carModel=sc.nextLine();
                    carModel = carModel.substring(0, 1).toUpperCase()+carModel.substring(1).toLowerCase();  //set input according to our record
                    carInfo=MarutiCars.get(carModel);                                                        //getting information of car selected by customer 
                    if(carInfo==null)                                                                        
                    System.out.println("no carModel found.\nPlease enter again");
                }                                      
            }
            else if(carBrand.equalsIgnoreCase("toyota"))
            {
                printStar();
                displayToyotaCars();
                printStar();
                while(carInfo==null)
                {
                    System.out.println("Enter car model");
                    carModel=sc.nextLine();
                    carModel = carModel.substring(0, 1).toUpperCase()+carModel.substring(1).toLowerCase();
                    carInfo=ToyotaCars.get(carModel);
                    if(carInfo==null)
                    System.out.println("no carModel found.\nPlease enter again");
                }
            }
            else if(carBrand.equalsIgnoreCase("Hyundai"))
            {
                printStar();
                displayHyundaiCars();
                printStar();
                while(carInfo==null)
                {
                    System.out.println("Enter car model");
                    carModel=sc.nextLine();
                    carModel = carModel.substring(0, 1).toUpperCase()+carModel.substring(1).toLowerCase();
                    carInfo=HyundaiCars.get(carModel);
                    if(carInfo==null)
                    System.out.println("no carModel found.\nPlease enter again");
                }
            }
            else 
            {
                System.out.println("Sorry!\nWe only have cars of MARUTI ,TOYOTA and HYUNDAI brand");
                carBrand=null;
            }
        }
        return carInfo;
    }
    static void generatePrizes()                              //select lucky customers to give prizes; 
    {
        Random random=new Random();
       
       int f=0;
        if(CustomersDetails.size()<6)
        {  
            System.out.println("NOT ENOUGH CUSTOMERS.\nPLEASE ADD MORE CUSTOMERS");
            return;
        }
      
        for(int i=0;i<3;i++)
        {
            System.out.println("Enter id ");  //3 different customer ids selected by admin
            int enteredId=getInput();
               
            if(adminSelected.contains(enteredId))
            { 
                System.out.println("Id already selected.\nPleasa select another.");
                    i--;
            } 
            else if(enteredId>CustomersDetails.size()||enteredId<=0)
            {
                 System.out.println("ID does not exist.\nPleasa select another");
                 i--;
            }
            else
                adminSelected.add(enteredId);
        }
          System.out.println("CUSTOMERS WHO ARE ELIGIBLE FOR PRIZES ARE: ");
        for(int i=0;i<6;i++)
        {
            int randomNumber=random.nextInt(CustomersDetails.size()+1);               //6 different ids generated randomly 
            
            if(randomSelected.contains(randomNumber)||randomNumber<=0)
            {
                i--;
            }
            else
            {
                randomSelected.add(randomNumber);
                if(adminSelected.contains(randomNumber))                           //if random selected id match with admin selected id  
                {
                    f=1;                                                           //customer with matched id is eligible for prize
                    System.out.print(randomNumber+" ");
                }
            }
        }
        if(f==0)
            System.out.println(" none.");                                         //if no id matches 
        else 
            System.out.println("");
    }
    static void printStar()                                   //print several * to differentiate different tasks
    {
        System.out.println("***********************************************************************************************");
    }
    static void adminFunctions()                               //definition of admin's function
    {
        String selectedOption=displayAdminFunctions();
        do
        {
             switch(selectedOption)
            {
               case "1":                                                                       // add new customer               
                   System.out.println("Enter customer name: ");
                   String customerName=sc.nextLine();
                   Customer customerDetails=new Customer(++customer_Id,customerName,addCar()); //add deatils of car purchased and id to customer data
                   CustomersDetails.put(customer_Id,customerDetails);                          // add customer data to list of all customers
                   //SortedCustomersDetails.put(customerDetails,customer_Id);
                   printStar();
                   System.out.println("Id of customer is : "+customer_Id);
                   printStar();
                   break;
               case "2":                                                                     //add new car to existing customer
                   if(CustomersDetails.isEmpty())
                   {
                       System.out.println("No customer to add car");
                       //displayAdminFunction();
                       
                   }
                   else
                   {   int f=0;
                       int idToAddCar=-1;
                        while(idToAddCar==-1)
                        {
                            System.out.println("Enter customer id: ");
                            idToAddCar=getInput();
                            if(idToAddCar>CustomersDetails.size()||idToAddCar<=0)
                            {
                                System.out.println("Id does not exist");
                                idToAddCar=-1;
                                f=1;
                                
                            }
                            if(f==1)
                            {
                                System.out.println("enter 1 to exit and 2 to see menu and any integer  to enter again");
                                int userChoice=getInput();
                                switch (userChoice) 
                                {
                                    case 1:
                                        return;
                                    case 2:
                                        adminFunctions();
                                        return ;
                                    default:
                                        f=0;
                                        break;
                                }
                            }    
                        }
                        Customer customer=CustomersDetails.get(idToAddCar);          //get customer's details from list of all customer
                        ArrayList newCarsList=customer.carsList;                     //get list of already purchased cars
                        newCarsList.add(addCar());
                        customer.carsList=newCarsList;                                 // add new car to purchased cars
                        printStar();
                   }
                   
                   break;
               case "3":             //display details of all custometer
                   if(CustomersDetails.isEmpty())
                   {
                       System.out.println("No customer to show");
                      
                   }
                   else
                   {
                       printSortedDetails();
                   }
                   
                   break;
               case "4":                                                                //show details of particular customer
                   if(CustomersDetails.isEmpty())
                   {
                       System.out.println("No customer to show");
                      
                   }
                   else
                   {
                       int idToSeeDetails=-1;
                       int f=0;
                        while(idToSeeDetails==-1)
                        {
                            System.out.println("Enter id of customer to see details");
                            idToSeeDetails=getInput();
                            if(idToSeeDetails>CustomersDetails.size()||idToSeeDetails<=0)
                            {
                                System.out.println("Id not found");
                                idToSeeDetails=-1;
                                f=1;
                            }
                            if(f==1)
                            {
                                System.out.println("enter 1 to exit and 2 to see menu and any integer  to enter again");
                                int userChoice=getInput();
                                switch (userChoice) {
                                    case 1:
                                        return;
                                    case 2:
                                        adminFunctions();
                                        return ;
                                    default:
                                        f=0;
                                        break;
                                }
                            }
                        }
                        Customer customer1=CustomersDetails.get(idToSeeDetails);                       //get details uusing id  from all customers list
                        customer1.displayCustomerDetails();                                            //display details of customer
                        printStar();
                   }
                   
                   break;
               case "5":                                //select lucky customers to gize prizes  
                   generatePrizes();
                   printStar();
                   break;
               default:
                   return;
             }     
             if(!(selectedOption.equals("6")))
             {
                 selectedOption=displayAdminFunctions();
             }
        }
        while(!(selectedOption.equals("6")));
    }
    
    public static void main(String args[])
    {
        marutiCars(1,"Swift",586448);
        marutiCars(2,"Ciaz",820000);
        marutiCars(3,"Vitara",2270000);
        toyotaCars(4,"Etios",696125);
        toyotaCars(5,"Corolla",1645000);
        toyotaCars(6,"Fortuner",2785000);
        hyundaiCars(7,"Eon",468000);
        hyundaiCars(8,"Santro",450000);
        hyundaiCars(9,"Creta",960000);
        adminFunctions();
        
    }
    
}
