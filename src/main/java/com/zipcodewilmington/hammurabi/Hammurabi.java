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
    int grainToFeed = 0;
    int peopleFed = 0;
    int acresPlanted = 0;
    int grainFactor = 3;
    int grainHarvested = 3000;
    int peopleStarved = 0;
    int newCitizens = 5;
    int ratGrains = 200;
    int totalStarved = 0;
    boolean plague = false;
    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        while (true){
            newYear();
            if (isGameOver()){
                break;
            }
            printSummary();
            int acresBought = askHowManyAcresToBuy();
            this.acresOwned += acresBought;
            if (acresBought == 0){
                this.acresOwned -= askHowManyAcresToSell();
            }
            this.grainToFeed = askHowMuchGrainToFeedPeople();
            this.acresPlanted += askHowManyAcresToPlant();
        }
        finalSummary();
    }

    void printSummary(){
        System.out.println("\n\nO great Bababouie!");
        System.out.printf("You are in year %d of your ten year rule.\n", currentYear);
        System.out.printf("In the previous year %d people starved to death.\n", peopleStarved);
        System.out.printf("In the previous year %d people entered the kingdom.\n", newCitizens);
        if (plague) {
            System.out.println("A horrible plague has struck! Half of the population has died...");
        }
        System.out.printf("The population is now %d.\n", population);
        System.out.printf("We harvested %d bushels at %d bushels per acre.\n", grainHarvested, grainFactor);
        System.out.printf("Rats destroyed %d bushels, leaving %d bushels in storage.\n", ratGrains, grainInStorage);
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
                acresPlanted = 0; // Resetting value
            }
            else {
                grainHarvested = 0; // Resetting value
                grainFactor = 0; // Resetting value
            }
            peopleStarved = starvationDeaths(population, grainToFeed);
            totalStarved += peopleStarved;
            if (peopleStarved < 1) {
                newCitizens = immigrants(population, acresOwned, grainInStorage);
                population += newCitizens;
            }
            else {
                newCitizens = 0; // Resetting value
            }
            ratGrains = grainEatenByRats(grainInStorage);
            grainInStorage -= ratGrains;
            int plagueDeaths = plagueDeaths(population);
            if (plagueDeaths > 0) {
                population -= plagueDeaths;
                plague = true;
            }
            else {
                plague = false; // Resetting value
            }
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
                if (proposal > 0) {
                    System.out.println("You now have " + this.grainInStorage + " bushels.");
                }
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
                if (proposal > 0) {
                    System.out.println("You now have " + this.grainInStorage + " bushels.");
                }
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
                if (proposal > 0) {
                    System.out.println("You now have " + this.grainInStorage + " bushels.");
                }
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
                return proposal;
            }
        }
    }

    public int plagueDeaths(int population) {
        // 15% chance of plague - 50% population dies
        if (rand.nextInt(100) < 15){
            return population / 2;
        }
        return 0;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        // Each person needs 20 bushels per year -
        peopleFed = (bushelsFedToPeople / 20);
        if (peopleFed > population){
            return 0;
        }
        int peopleStarved = population - peopleFed;
        this.population -= peopleStarved;
        return peopleStarved;
    }

    public boolean uprising(int population, int peopleDead) {
        // if more than 45% of people starve, return true -
        return (double) peopleDead / population > .45;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
        // Don't call if anyone starves
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    public int harvest(int acres) { // , int bushelsUsedAsSeed
        grainFactor = rand.nextInt(6) + 1;
        return acres * (grainFactor);
    }

    public int grainEatenByRats(int bushels) {
        // 40% chance of rat infestation, rats eat between 10-30% of grainInStorage
        if (rand.nextInt(100) < 40) {
            return bushels * (rand.nextInt(21) + 10) / 100;
        }
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
            case "grain": reason = "O Great Bababouie, quit playin! We only got " + this.grainInStorage + " bushels left!";
                break;
            case "land": reason = "O Great Bababouie, u fr rn? We only got " + this.acresOwned + " acres of land!";
                break;
            case "people": reason = "O Great Bababouie, u wild for that! We only got " + this.population + " people to tend the fields!";
                break;
            case "negative": reason = "Bababouie, respectfully, ur tweakin ma boi...";
                break;
        }
        System.out.println(reason);
    }

    boolean isGameOver(){
        return this.currentYear > 10 || uprising(population, peopleStarved);
    }

    void finalSummary(){
        if (uprising(population, peopleStarved)){
            System.out.println("\n\nThis u bro? U some kinda clown? Better luck next time, bozo.\n\n");
            System.out.println("                 ,            _..._            ,\n" +
                    "                {'.         .'     '.         .'}\n" +
                    "               { ~ '.      _|=    __|_      .'  ~}\n" +
                    "              { ~  ~ '-._ (___________) _.-'~  ~  }\n" +
                    "             {~  ~  ~   ~.'           '. ~    ~    }\n" +
                    "            {  ~   ~  ~ /   /\\     /\\   \\   ~    ~  }\n" +
                    "            {   ~   ~  /    __     __    \\ ~   ~    }\n" +
                    "             {   ~  /\\/  -<( o)   ( o)>-  \\/\\ ~   ~}\n" +
                    "              { ~   ;(      \\/ .-. \\/      );   ~ }\n" +
                    "               { ~ ~\\_  ()  ^ (   ) ^  ()  _/ ~  }\n" +
                    "                '-._~ \\   (`-._'-'_.-')   / ~_.-'\n" +
                    "                    '--\\   `'._'\"'_.'`   /--'\n" +
                    "                        \\     \\`-'/     /\n" +
                    "                         `\\    '-'    /'\n" +
                    "                           `\\       /'\n" +
                    "                             '-...-'\n" +
                    "\n");
        }
        else if (totalStarved > 10 || acresOwned/population < 10){
            System.out.println("\n\nGrade: D+\n\n");
            System.out.println(" ___________________________ \n" +
                    "< Got me in a bad MOOd rn bruh >\n" +
                    " ------------------------------ \n" +
                    "        \\   ^__^\n" +
                    "         \\  (xx)\\_______\n" +
                    "            (__)\\       )\\/\\\n" +
                    "             U  ||----w |\n" +
                    "                ||     ||");
        }
        else if (totalStarved > 3 || acresOwned/population == 10){
            System.out.println("\n\nGrade: C+\n\n");
            System.out.println(" _______________________\n" +
                    "< Mid at best.  >\n" +
                    " -----------------------\n" +
                    "   \\\n" +
                    "    \\\n" +
                    "        .--.\n" +
                    "       |o_o |\n" +
                    "       |:_/ |\n" +
                    "      //   \\ \\\n" +
                    "     (|     | )\n" +
                    "    /'\\_   _/`\\\n" +
                    "    \\___)=(___/");
        }
        else {
            System.out.println("\n\nGrade: A+\n\n.");
            System.out.println(" ___________________________________________\n" +
                    "< Okay I pull up! Hop out at the after party!  >\n" +
                    " ---------------------------------------------\n" +
                    "      \\                    / \\  //\\\n" +
                    "       \\    |\\___/|      /   \\//  \\\\\n" +
                    "            /0  0  \\__  /    //  | \\ \\\n" +
                    "           /     /  \\/_/    //   |  \\  \\\n" +
                    "           @_^_@'/   \\/_   //    |   \\   \\\n" +
                    "           //_^_/     \\/_ //     |    \\    \\\n" +
                    "        ( //) |        \\///      |     \\     \\\n" +
                    "      ( / /) _|_ /   )  //       |      \\     _\\\n" +
                    "    ( // /) '/,_ _ _/  ( ; -.    |    _ _\\.-~        .-~~~^-.\n" +
                    "  (( / / )) ,-{        _      `-.|.-~-.           .~         `.\n" +
                    " (( // / ))  '/\\      /                 ~-. _ .-~      .-~^-.  \\\n" +
                    " (( /// ))      `.   {            }                   /      \\  \\\n" +
                    "  (( / ))     .----~-.\\        \\-'                 .~         \\  `. \\^-.\n" +
                    "             ///.----..>        \\             _ -~             `.  ^-`  ^-_\n" +
                    "               ///-._ _ _ _ _ _ _}^ - - - - ~                     ~-- ,.-~\n" +
                    "                                                                  /.-~          ");
        }
    }
}