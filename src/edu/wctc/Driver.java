package edu.wctc;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
    Terminal test = new Terminal();
        ArrayList<Ticket> checkIn = new ArrayList<>();
        ArrayList<Ticket> checkOuts = new ArrayList<>();
        double checkOutCollected = 0.0;
        double totalCollected = 0.0;
        int checkIns = 0;
        int lostTickets = 0;

        test.startTerminal(checkIn, checkOuts, checkOutCollected, totalCollected, checkIns, lostTickets);



    }
}
