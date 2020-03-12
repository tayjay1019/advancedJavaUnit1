package edu.wctc;

import java.time.LocalTime;
import java.util.Random;

public class Ticket {

    private int ticketID;
    public static int counter = 0;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private double cost;

    public Ticket () {
        Random hour = new Random();
        Random minute = new Random();
        counter++;
        ticketID = counter;
        checkIn = LocalTime.of(hour.nextInt(5) + 7, minute.nextInt(60));
    }

    public Ticket (int ticketID, LocalTime checkIn) {
        counter++;
        this.ticketID = ticketID;
        this.checkIn = checkIn;
    }

    public int getTicketID() {
        return ticketID;
    }


    public LocalTime getCheckIn() {
        return checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut() {
        Random hour = new Random();
        Random minute = new Random();
        this.checkOut = LocalTime.of(hour.nextInt(10)+ 13, minute.nextInt(60));
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String toString() {return ticketID + "/" + checkIn;}
}
