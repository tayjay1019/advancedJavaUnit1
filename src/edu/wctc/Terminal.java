package edu.wctc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Terminal {

    static int ticketCounter = 0;
    static double checkOutTotal = 0.0;

    public static void startTerminal(ArrayList<Ticket> checkInTickets, ArrayList<Ticket> checkOuts, double checkOutCollected, double totalCollected, int checkIns, int lostTickets) throws FileNotFoundException {
        String filename = "ticket.txt";
        Scanner keyboard = new Scanner(System.in);
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        while (inputFile.hasNext())
        {
            String listener = inputFile.nextLine();
            String[] str = listener.split("/");
            int id = Integer.parseInt(str[0]);
            LocalTime in = LocalTime.parse(str[1]);
            Ticket past = new Ticket(id,in);
            checkInTickets.add(past);
        }

        PrintWriter outputFile = new PrintWriter(file);

        boolean exit = false;
        do {
            terminalMenu();
            String select = keyboard.nextLine();
            terminalSelect(select, checkInTickets, checkOuts, checkOutCollected, checkIns, lostTickets, totalCollected, outputFile);
        } while (!exit);

    }

    public static void terminalMenu() {
        System.out.println("------ Best Value Parking Garage -----");
        System.out.println("Which machine do you want to access?");
        System.out.println("1) Check-In machine");
        System.out.println("2) Check-Out machine");
    }

    public static void checkInMenu() {
        System.out.println("------ Best Value Parking Garage -----");
        System.out.println("1) Check-In");
        System.out.println("2) Close garage");
    }

    public static void checkOutMenu() {
        System.out.println("------ Best Value Parking Garage -----");
        System.out.println("1) Check-Out");
        System.out.println("2) Lost Ticket");
    }

    public static void terminalSelect(String select, ArrayList<Ticket> checkInTickets, ArrayList<Ticket> checkOuts, double checkOutCollected, int checkIns, int lostTickets, double totalCollected, PrintWriter outputFile) {
       boolean exit = false;
       Scanner keyboard = new Scanner(System.in);
       String secondSelect;
        switch (select){
            case "1":
                do {
                    checkInMenu();
                    secondSelect = keyboard.nextLine();
                    exit = checkInSelect(secondSelect, checkInTickets, checkOuts, checkIns, totalCollected, outputFile);
                } while (!exit);
                break;
            case "2":
                do {
                    checkOutMenu();
                    secondSelect = keyboard.nextLine();
                   exit = checkOutSelect(secondSelect, checkInTickets, checkOuts, checkOutCollected, lostTickets);
                } while (!exit);
                break;
            default:
                System.out.println("Not a valid input");
        }
    }

    public static boolean checkInSelect(String select, ArrayList<Ticket> checkInTickets, ArrayList<Ticket> checkOuts, int checkIns, double totalCollected, PrintWriter outputFile) {
        boolean exit = false;
        switch (select){
            case "1":
                checkInTicket(checkInTickets, checkIns);
                break;
            case "2":
                closeGarage(checkOuts, outputFile, totalCollected, checkInTickets);
                break;
            case "terminal":
                exit = true;
                break;
            default:
                System.out.println("Not a valid input");
        }
        return exit;
    }

    public static boolean checkOutSelect(String select, ArrayList<Ticket> checkInTickets, ArrayList<Ticket> checkOuts, double checkOutCollected, int lostTickets) {
        boolean exit = false;
        switch (select){
            case "1":
                checkOutTicket(checkInTickets, checkOuts, checkOutCollected);
                break;
            case "2":
                lostTicket(lostTickets);
                break;
            case "terminal":
                exit = true;
                break;
            default:
                System.out.println("Not a valid input");
        }
        return exit;
    }

    public static void checkInTicket(ArrayList<Ticket> checkInTickets, int checkIns) {
        Ticket t = new Ticket();
        checkInTickets.add(t);
        System.out.println("Vehicle " + t.getTicketID() + " has checked in");
        checkIns++;
    }

    public static void checkOutTicket(ArrayList<Ticket> checkInTickets, ArrayList<Ticket> checkOuts, double checkOutCollected) {
       if (checkInTickets.size() < 1)
       {
           System.out.println("There are no tickets");
       }
       else {
           Random id = new Random();

           int test = id.nextInt(checkInTickets.size());
           Ticket t = checkInTickets.get(test);
           checkInTickets.remove(test);
           t.setCheckOut();

           Duration duration = Duration.between(t.getCheckIn(), t.getCheckOut());
           System.out.println("Test: " + duration);
           double hoursBetween = duration.toHoursPart();
           double cost;
           if(hoursBetween <= 3.0)
           {
              cost = 5.0;
           }
           else if(hoursBetween > 3.0 && hoursBetween <= 4)
           {
               cost = 6.0;
           }
           else if(hoursBetween > 4.0 && hoursBetween <= 5)
           {
               cost = 7.0;
           }
           else if(hoursBetween > 5.0 && hoursBetween <= 6)
           {
               cost = 8.0;
           }
           else if(hoursBetween > 6.0 && hoursBetween <= 7)
           {
               cost = 9.0;
           }
           else if(hoursBetween > 7.0 && hoursBetween <= 8)
           {
               cost = 10.0;
           }
           else if(hoursBetween > 8.0 && hoursBetween <= 9)
           {
               cost = 11.0;
           }
           else if(hoursBetween > 9.0 && hoursBetween <= 10)
           {
               cost = 12.0;
           }
           else if(hoursBetween > 10.0 && hoursBetween <= 11)
           {
               cost = 13.0;
           }
           else if(hoursBetween > 11.0 && hoursBetween <= 12)
           {
               cost = 14.0;
           }
           else
           {
               cost = 15.0;
           }

           t.setCost(cost);
            checkOuts.add(t);
            checkOutTotal += cost;
           System.out.println("Receipt ticket #" + t.getTicketID());
           System.out.printf("Cost: $%.2f\n", t.getCost());
       }
    }

    public static void lostTicket(int lostTickets) {

        ticketCounter++;
        System.out.println("Receipt for unknown ticket");
        System.out.println("Lost ticket $25.00");
        System.out.println("lost tickets " + ticketCounter);


    }

    public static void closeGarage(ArrayList<Ticket> checkOuts, PrintWriter outputFile, double totalCollected, ArrayList<Ticket> checkInTickets) {

        double lostCost = ticketCounter * 25.0;
        totalCollected = checkOutTotal + lostCost;

        System.out.println("------ Best Value Parking Garage -----");
        System.out.println("\nActivity to Date\n");
        System.out.printf("$%.2f was collected from %d Check-Ins\n\n", checkOutTotal, checkOuts.size());
        System.out.printf("$%.2f was collected from %d Lost Tickets\n\n", lostCost, ticketCounter);
        System.out.printf("$%.2f was collected overall\n", totalCollected);

        for( Ticket tickets : checkInTickets) {
            outputFile.println(tickets);
        }
        outputFile.close();
        System.exit(0);
    }

}
