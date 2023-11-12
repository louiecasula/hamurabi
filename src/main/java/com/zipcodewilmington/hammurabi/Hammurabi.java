package com.zipcodewilmington.hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    int price = 19;
    int bushels = 2800;
    int acresOwned = 1000;
    int population = 100;
    int currentYear = 0;
    int peopleFed = 0;
    int acresPlanted = 0;
    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        while (!isGameOver()){
            newYear();
            printSummary();
            int acresBought = askHowManyAcresToBuy();
            this.acresOwned += acresBought;
            if (acresBought == 0){
                this.acresOwned -= askHowManyAcresToSell();
            }
            this.peopleFed += (askHowMuchGrainToFeedPeople() / 20);
            this.acresPlanted += askHowManyAcresToPlant();
        }
    }

    void printSummary(){
        System.out.println("\n\nO great Hammurabi!");
        System.out.printf("You are in year %d of your ten year rule.\n", currentYear);
        System.out.printf("In the previous year 0 people starved to death.\n");
        System.out.printf("In the previous year 5 people entered the kingdom.\n");
        System.out.printf("The population is now %d.\n", population);
        System.out.printf("We harvested 3000 bushels at 3 bushels per acre.\n");
        System.out.printf("Rats destroyed 200 bushels, leaving %d bushels in storage.\n", bushels);
        System.out.printf("The city owns %d acres of land.\n", acresOwned);
        System.out.printf("Land is currently worth %d bushels per acre.\n", price);
    }

    void newYear(){
        this.currentYear++;
        if (this.currentYear > 1){
            this.price = newCostOfLand();
        }
    }

    int askHowManyAcresToBuy(){
        while (true){
            int proposal = getNumber("How many acres of land do you wish to buy? ");
            if (proposal < 0) {
                sanityCheck("negative");
            }
            else if (proposal * this.price > this.bushels) {
                sanityCheck("grain");
            }
            else {
                this.bushels -= (proposal * this.price);
                System.out.println("You now have " + this.bushels + " bushels.");
                return proposal;
            }
        }
    }

    int askHowManyAcresToSell(){
        while (true){
            int proposal = getNumber("How many acres of land do you wish to sell? ");
            if (proposal < 0) {
                sanityCheck("negative");
            }
            else if (proposal > this.acresOwned) {
                sanityCheck("land");
            }
            else {
                this.bushels += (proposal * this.price);
                System.out.println("You now have " + this.bushels + " bushels.");
                return proposal;
            }
        }
    }

    int askHowMuchGrainToFeedPeople(){
        while (true){
            int proposal = getNumber("How many bushels do you wish to feed your people? ");
            if (proposal < 0) {
                sanityCheck("negative");
            }
            else if (proposal > this.bushels) {
                sanityCheck("grain");
            }
            else {
                this.bushels -= proposal;
                System.out.println("You now have " + this.bushels + " bushels.");
                return proposal;
            }
        }
    }

    int askHowManyAcresToPlant(){
        while (true){
            int proposal = getNumber("How many acres of land do you wish to plant with seed? ");
            if (proposal < 0) {
                sanityCheck("negative");
            }
            else if (proposal > (this.population * 10)) {
                sanityCheck("people");
            }
            else if (proposal > this.bushels) {
                sanityCheck("grain");
            }
            else if (proposal > this.acresOwned) {
                sanityCheck("land");
            }
            else {
                this.bushels -= proposal;
                System.out.println("You now have " + this.bushels + " bushels.");
                return proposal;
            }
        }
    }

    // Do this later...
    public int plagueDeaths(int population) {
        return 0;
    }

    public int starvationDeaths(int i, int i1) {
        return 0;
    }

    public boolean uprising(int population, int bushelsFedToPeople) {
        return false;
    }

    // Do this later...
    public int immigrants(int population, int acresOwned, int grainInStorage) {
        return 0;
    }

    public int harvest(int acres) { // , int bushelsUsedAsSeed
        return 0;
    }

    // Do this later...
    public int grainEatenByRats(int bushels) {
        return 0;
    }

    public int newCostOfLand() {
        return rand.nextInt(7) + 17;
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    void sanityCheck(String x) {
        String reason = "";
        switch(x) {
            case "grain": reason = "O Great Hammurabi, surely you jest! We have only " + this.bushels + " bushels left!";
                break;
            case "land": reason = "O Great Hammurabi, surely you jest! We have only " + this.acresOwned + " acres of land!";
                break;
            case "people": reason = "O Great Hammurabi, we have only " + this.population + " people to tend the fields!";
                break;
            case "negative": reason = "Hammurabi, I cannot do what you wish. Get yourself another steward!!";
                break;
        }
        System.out.println(reason);
    }

    boolean isGameOver(){
        return this.currentYear >= 10 || this.population < 1;
    }
}