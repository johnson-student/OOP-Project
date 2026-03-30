package controller;
import java.util.Scanner;
import user.User;
import other.Address;
public class Main {

    public static void main(String[] args) {
    
        DeliverySystem system = new DeliverySystem("CADT Delivery", "013261425","Phnom Penh","Duan Penh","152");
        System.out.println(system.getLocation());
        system.runCode();

      }
}