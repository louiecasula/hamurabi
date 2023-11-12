package com.zipcodewilmington.hammurabi;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);
    int price;
    int bushels;
    int acresOwned;
    int population;
    int currentYear;
    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        this.currentYear = 1;
        this.population = 100;
        this.bushels = 2800;
        this.acresOwned = 1000;
        this.price = 19;
        yearlyReport();
    }

    void yearlyReport(){
        System.out.println("O great Hammurabi!");
        System.out.printf("You are in year %d of your ten year rule.\n", currentYear);
        System.out.printf("In the previous year 0 people starved to death.\n");
        System.out.printf("In the previous year 5 people entered the kingdom.\n");
        System.out.printf("The population is now %d.\n", population);
        System.out.printf("We harvested 3000 bushels at 3 bushels per acre.\n");
        System.out.printf("Rats destroyed 200 bushels, leaving %d bushels in storage.\n", bushels);
        System.out.printf("The city owns %d acres of land.\n", acresOwned);
        System.out.printf("Land is currently worth %d bushels per acre.\n", price);
    }

    int askHowManyAcresToBuy(int price, int bushels){

        return 0;
    }

    int askHowManyAcresToSell(int acresOwned){
        return 0;
    }

    int askHowMuchGrainToFeedPeople(int bushels){
        return 0;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        return 0;
    }

    // Do these later...
    public int plagueDeaths(int i) {
        return 0;
    }

    public int starvationDeaths(int i, int i1) {
        return 0;
    }

    public boolean uprising(int i, int i1) {
        return false;
    }

    public int immigrants(int i, int i1, int i2) {
        return 0;
    }

    public int harvest(int i) {
        return 0;
    }

    public int grainEatenByRats(int i) {
        return 0;
    }

    public int newCostOfLand() {
        return 0;
    }
}