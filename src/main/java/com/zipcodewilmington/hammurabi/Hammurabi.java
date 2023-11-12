package com.zipcodewilmington.hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    int price = 19;
    int grainInStorage = 2800;
    int acresOwned = 1000;
    int population = 100;
    int currentYear = 0;
    int peopleFed = 0;
    int acresPlanted = 0;
    int grainFactor = 3;
    int grainHarvested = 3000;
    int peopleStarved = 0;
    int newCitizens = 5;
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
            this.acresPlanted += askHowManyAcresToPlant();
        }
    }

    void printSummary(){
        System.out.println("\n\nO great Hammurabi!");
        System.out.printf("You are in year %d of your ten year rule.\n", currentYear);
        System.out.printf("In the previous year %d people starved to death.\n", peopleStarved);
        System.out.printf("In the previous year %d people entered the kingdom.\n", newCitizens);
        System.out.printf("The population is now %d.\n", population);
        System.out.printf("We harvested %d bushels at %d bushels per acre.\n", grainHarvested, grainFactor);
        System.out.printf("Rats destroyed 200 bushels, leaving %d bushels in storage.\n", grainInStorage);
        System.out.printf("The city owns %d acres of land.\n", acresOwned);
        System.out.printf("Land is currently worth %d bushels per acre.\n", price);
    }

    void newYear(){
        currentYear++;
        if (currentYear > 1){
            price = newCostOfLand();
            if (acresPlanted > 0) {
                grainHarvested = harvest(acresPlanted);
                grainInStorage += grainHarvested;
                acresPlanted = 0;
            }
            peopleStarved = starvationDeaths(population, peopleFed);
        }
    }

    int askHowManyAcresToBuy(){
        while (true){
            int proposal = getNumber("How many acres of land do you wish to buy? ");
            if (proposal < 0) {
                sanityCheck("negative");
            }
            else if (proposal * this.price > this.grainInStorage) {
                sanityCheck("grain");
            }
            else {
                this.grainInStorage -= (proposal * this.price);
                System.out.println("You now have " + this.grainInStorage + " bushels.");
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
                this.grainInStorage += (proposal * this.price);
                System.out.println("You now have " + this.grainInStorage + " bushels.");
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
            else if (proposal > this.grainInStorage) {
                sanityCheck("grain");
            }
            else {
                this.grainInStorage -= proposal;
                System.out.println("You now have " + this.grainInStorage + " bushels.");
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
            else if (proposal > this.grainInStorage) {
                sanityCheck("grain");
            }
            else if (proposal > this.acresOwned) {
                sanityCheck("land");
            }
            else {
                this.grainInStorage -= proposal;
                System.out.println("You now have " + this.grainInStorage + " bushels.");
                return proposal;
            }
        }
    }

    // Do this later...
    public int plagueDeaths(int population) {
        // 15% chance of plague - 50% population dies
        return 0;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        // Each person needs 20 bushels per year -
        peopleFed = (bushelsFedToPeople / 20);
        if (peopleFed > population){
            return 0;
        }
        peopleStarved = population - peopleFed;
        this.population -= peopleStarved;
        return peopleStarved;
    }

    public boolean uprising(int population, int bushelsFedToPeople) {
        // if more than 45% of people starve, return true -
        // peopleFed = bushelsFedToPeople / 20
        // population - peopleFed = starvationDeaths
        // if starvationDeaths / population > .45 , return true
        return false;
    }

    // Do this later...
    public int immigrants(int population, int acresOwned, int grainInStorage) {
        // Don't call if anyone starves
        // (20 * acresOwned + grainInStorage) / (100 * population) + 1.
        return 0;
    }

    public int harvest(int acres) { // , int bushelsUsedAsSeed
        grainFactor = rand.nextInt(6) + 1;
        return acres * (grainFactor);
    }

    // Do this later...
    public int grainEatenByRats(int bushels) {
        // 40% chance of rat infestation, rats eat between 10-30% of grainInStorage
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
            case "grain": reason = "O Great Hammurabi, surely you jest! We have only " + this.grainInStorage + " bushels left!";
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

    boolean isGameOver(){ // Doesn't end immediately... fix this.
        return this.currentYear >= 10 || this.population < 1;
    }
}