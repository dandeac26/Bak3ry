## Programming Techniques Assignment

# FOOD DELIVERY MANAGEMENT SYSTEM



![Login and logo of the application](images/Picture1.png)

##

## Overview

- Language: Java 100%
- IDE: Intellij
- GUI: JavaFX

![Shape2](RackMultipart20230414-1-94ax43_html_42a77781f880c8a4.gif)

![](RackMultipart20230414-1-94ax43_html_3c900a20f90c38ae.png)

Here is a short overview of the GUI:

On the left side there is the employee side of the application and on the right, it's the client side. The client can add items to the order by searching from the menu, selecting the item and pressing add. The total order will be updated and the client can submit the order or clear items form the order by selecting them and clicking clear.

Once the order is submitted by the client, a bill is generated on the client side and the employee GUI gets updated with the new order.

This project implements the observer design pattern.

The communication is not done through a server because this is more of a demo to showcase the implementation of the design pattern, everything is done locally and those views are part of the same instance of the application.

The admin has a special view where he can generate reports based on the orders the app received.

1.
## Assignemnt Objective

The objective of this assignment is to implement a food delivery management system for a restaurant/catering company. The main functionalities of the application are to enable clients to register and log in into the application, and choose from the company's menu their desired meal, and then submit the order. The client will receive a bill containing his information and the order will be sent to the employees of the business. They will be notified in real time about incoming orders. The app will also have accounts with administrator access. They can update, add, and delete products from the app's menu in order to maintain everything updated. They can also generate several reports to gather some information about how certain products are performing.

## Use case diagram:

![](RackMultipart20230414-1-94ax43_html_90e55bdd381d5967.png)

## 2.Handling unintended scenarios

The application is not a deployable ready application and has more or less the purpose of the enabling students to practice and learn. Therefore some of the functionalities are not expected to work in the real world. This being said, the application still tries it's best to handle unexpected/ unwanted scenarios. It tries to imitate real life features like a search feature.

![](RackMultipart20230414-1-94ax43_html_4e31002ed0fabb65.png)

## 3 Design

Package diagram: ![](RackMultipart20230414-1-94ax43_html_cba343ed3dda856c.png)

UML Class diagram:

![](RackMultipart20230414-1-94ax43_html_78754ec6d9ff630b.png)

1.
## Implementation

This Class uses Composite Design Pattern in order to generate a menu items'prices.

These classes are used to hold the required data which will be used in the DeliveryService.

This project also uses the Observer Design Pattern.

```java
public interface Observer\<T\> {
void update(PropertyChanged\<T\> args);
}
```

There are several objects: Employee, Client, Administrator that make use of the observer to update themselves when important changes happen to the DeliveryService class which is Observable. The Delivery Service class holds the main Arrays used to hold the data and represent it.

```java
public class Observable\<T\> {

private List\<Observer\<T\>\> observers = new ArrayList\<\>();

 public void addObserver(Observer\<T\> observer) {
observers.add(observer);
}

protected void notifyObservers(T source, String propertyName, Object newValue) {
final PropertyChanged\<T\> changedEventArgs = new PropertyChanged\<\>(source, propertyName, newValue);
 for (Observer\<T\> observer : observers) {
 observer.update(changedEventArgs);
}
 }
 }
 ```

The class Login implements Serializable in order to store username/password information in byte format.

It is capable of reading back the data and providing the Controller with the login information.

```java
public class Login implements java.io.Serializable{
private String username;
 private String password;
 private String filename = "users.txt";

 public Login(String username, String password){
this.password = password;
 this.username = username;
}
public void writeData(){
try {
 FileWriter myWriter = new FileWriter(filename, true);

myWriter.append(username).append(":").append(password).append("\n");
myWriter.close();
// System.out.println("success");
} catch (IOException e) {
 System._out_.println("An error occurred.");
e.printStackTrace();
}
 }
public List\<Login\> readData() throws IOException{
 Path path = Path._of_("users.txt");
List\<Login\> list;
list = Files._lines_(path)
 .map(line-\> {
 String[] fields = line.split(":");
 return new Login(fields[0],fields[1]);
}).toList();
 return list;
}…
```

The Order class stores the orderID and the ClientId along with the Data. IT is used as the Key in the map where the main data is stored about the MenuItems and Orders currently present in the application.

It is used by several Classes to go through HashMaps that hold important data, like the menu items.

The IDeliveryService Interface holds all the main operations and most important. It helps with populating the Map with the Orders and the List with the available menu items. Whenever such a method is called, we also notify all the observers in the DeliveryService to make sure everything works as expected.

```java
public interface IDeliveryService {
 List\<BaseProduct\> getMenuItems();

 void addOrder(Order order, List\<MenuItem\> list);

Map\<Order, List\<MenuItem\>\> getOrders();

 void addObserver(Observer\<IDeliveryService\> observer);

Integer getOrderTotal(Order order);

 void removeOrder(Order order);

 void removeBaseProduct(BaseProduct baseProduct);

 void addBaseProduct(BaseProduct baseProduct);

 void setMenuItems(List\<BaseProduct\> list);

 void removeItem(BaseProduct baseProduct);

 void setClientID(String name);

Integer getClientID();
}
```

The DeliveryService is built upon the singleton pattern. The class extends the Observable class and implements the IDeliveryService. It is called once using the getInstance Method.

This class also populates the MenuItem lists with the products.csv file by calling a static method .getData from the ReadCSVFile class. This by default reads the products.csv sent to us by the lab teacher, but it also can work with data if imported through the admin interface. There is products2.csv of smaller size meant for a better visualization of the updates present in the admin interface upon the menu items.

```java
public class ReadCSVFile {
public static List\<BaseProduct\> getData(Path path) {
 List\<BaseProduct\> products;

Set\<String\> titles = new HashSet\<\>();
 try {
 products = Files._lines_(path)
 .skip(1)
 .filter(line -\> !titles.contains(line.split(",")[0]))
 .map(line -\> {
 String[] fields = line.split(",");
titles.add(fields[0]);
 return new BaseProduct(fields[0], Double._parseDouble_(fields[1]), Integer._parseInt_(fields[2]),
Integer._parseInt_(fields[3]), Integer._parseInt_(fields[4]), Integer._parseInt_(fields[5]), Integer._parseInt_(fields[6]));

}).distinct().toList();
} catch (IOException e) {
 e.printStackTrace();
 return new ArrayList\<\>();
}
return new ArrayList\<\>(products);
}
```

There has been a lot of use of Lambda expressions in this project, here is an example where I used it to generate the orders in the Employees:

```java
@Override
public void update(PropertyChanged\<IDeliveryService\> args) {
//System.out.println(args.toString());
staffOrderList.setItems(FXCollections._observableArrayList_(
deliveryService.getOrders().entrySet().stream()
 .map(order -\> {
 String menuItems = order.getValue().stream()
 .map(MenuItem::getTitle)
 .collect(Collectors._joining_("\n"));
String total = deliveryService.getOrderTotal(order.getKey()).toString();

String result = String._format_("======Order %s:======\n%s\nTotal: %s$", order.getKey().getOrderID(), menuItems, total);

deleter.put(result, order.getKey());

 return result;
})
 .collect(Collectors._toList_()))
 );

}
```

This kind of structure was used in several parts in the project and make processing data a bit easier. To be noted is the fact that some ListViews(used for presenting data to the user in the GUI) are declared to contain String objects instead of BaseProducts. It made life a bit more complicated so I needed those expressions in order to work with data. It was a bit confusing at the beginning but then it came more and more natural. It was a steep learning curve.

As you can see there are several prompts to provide the user with instant information about what kind of information is expected and how to better format it. It is especially useful in the AdminScene where the user has multiple textboxes.

![](RackMultipart20230414-1-94ax43_html_5af7a617431797f6.png)

The user is able to go through multiple windows and still keep the information due to the IDeliveryService and DeliveryService classes. This is how the information about orders persists through different stages. This is useful for generating the reports later on. Login information is stores indefinetly, even after restarting the application.

![](RackMultipart20230414-1-94ax43_html_3c900a20f90c38ae.png)

The UI is easy to use because the user can easily search his desiered items, then just select them from the ListView and click Add. You have a list of your order and the price total. After clicking submit, the information will be available for the employee in his own window. The submit also generates a bill in .txt format for the user, in similar format as the employee's list view.

Here is an example of a Bill.txt:

========= Bill0 =========

Smoked-Salmon Quesadillas with Warm Tomatoes and Arugula

My Favorite Roast Turkey

My Favorite Roast Turkey

Date: 23/07/2022 20:49:13

========= Total: 62$ =========

1.
## Conclusions

In conclusion, I was expecting this project to be easy compared to others, but it turned out to be the hardest one yet. It required to implement several design patterns and workflows like lambda expressions, and it demanded a lot of trial and error. Everything seemed so fragile and could break so easily. I also learned some interesting tricks in Intellij IDE which made working a bit easier as I progressed.
