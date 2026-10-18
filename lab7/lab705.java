/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author kitti
 */
class Address {
    protected String street;
    protected String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressInfo() {
        return street + ", " + city;
    }
}

class Employee {
    protected int id;
    protected String name;
    protected double salary;
    protected Address address;

    public String getDetails() {
        return "ID: " + id + "\nName: " + name + "\nSalary: " + salary
                + "\nAddress: " + (address == null ? "-" : address.getAddressInfo());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

class Manager extends Employee {
    protected String parkingNo;

    public String getParkingNo() {
        return parkingNo;
    }

    public void setParkingNo(String parkingNo) {
        this.parkingNo = parkingNo;
    }

    public String getDetails() {
        return super.getDetails() + "\nParking No: " + parkingNo;
    }
}

public class lab705 {
    public static void main(String[] args) {
        Employee emp = new Manager();
        emp.setId(1001);
        emp.setName("Somchai Jaidee");
        emp.setSalary(55000);

        Address ad = new Address();
        ad.setStreet("99 Rama 2 Road");
        ad.setCity("Bangkok");
        emp.setAddress(ad);

        ((Manager) emp).setParkingNo("A-12");

        System.out.println(emp.getDetails());
    }
}