package Game;

import java.util.Random;
import java.util.Scanner;

public class testing {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //statische Variablen
    static int[] dungeon = new int[30];
    static int deathRoom = -1;
    static int roomNumber = 0;
    static String input;
    static int position;

    static int monsterGoldDrop;
    static int monsterDamage;
    static int monsterHp;
    static int monsterExp;
    static int monsterRandom;
    static int monsterDmgOvertime = 0;
    static String monsterName;

    static int itemAal38 = 1;

    static int playerCurrentLife = 100;
    static int playerMaxLife = 100;
    static int playerCurrentExp = 0;
    static int playerNeededExp = 100;
    static int playerReachedExp = 0;
    static int playerLevel = 1;
    static int playerStr = 0;
    static int playerDef;
    static int playerWeaponAtk;
    static int playerDamage;
    static int playerGold = 0;
    static int playerKilledMonsters = 0;
    static int playerEnteredRooms = 0;
    static int playerAbilityElectricEel = 0;
    static int playerAbilityLightningAura = 0;
    static boolean playerDead = false;

    static String headline = """
            ███████╗███████╗██╗         ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗███████╗██████╗
            ██╔════╝██╔════╝██║         ██╔══██╗██║   ██║████╗  ██║████╗  ██║██╔════╝██╔══██
            █████╗  █████╗  ██║         ██████╔╝██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝
            ██╔══╝  ██╔══╝  ██║         ██╔══██╗██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗
            ███████╗███████╗███████╗    ██║  ██║╚██████╔╝██║ ╚████║██║ ╚████║███████╗██║  ██║
            ╚══════╝╚══════╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═
            """;
    static int delay = 5;

    static Random rand = new Random();
    static Scanner entry = new Scanner(System.in);

    //NEUER PART
    enum Difficulty {
        AALGLATT,
        NORMAAL,
        BRUTAAL,
        QUAALVOLL
    }

    static Difficulty difficulty = Difficulty.NORMAAL;
    static double monsterHpMod = 1.0;
    static double monsterDmgMod = 1.0;
    static double goldMod = 1.0;
    static double expMod = 1.0;
    static double healMod = 1.0;
    static int shopInterval = 8;


    public static void main(String[] args) {
        System.out.println("Auf welcher Schwierigkeitsstufe möchtest du spielen?");
        System.out.println("1. Aalglatt - Gleite mühelos und ohne Widerstand hindurch.");
        System.out.println("2. NormAal - Ein ausgeglichenes Abenteuer im seichten Gewässer.");
        System.out.println("3. BrutAal - Hier weht dir eine scharfe Brise entgegen.");
        System.out.println("4. QuAalvoll - Nur für extrem leidensfähige und zähe Fische.");
        System.out.println("Wähle weise!!");
        System.out.print("Eingabe: ");
        input = entry.nextLine().toLowerCase();

        switch (input) {
            case "aalglatt" -> difficulty = Difficulty.AALGLATT;
            case "brutaal" -> difficulty = Difficulty.BRUTAAL;
            case "quaalvoll" -> difficulty = Difficulty.QUAALVOLL;
            default -> difficulty = Difficulty.NORMAAL;
        }
        difficultySettings();
        System.out.println("Du hast die Schwierigkeit " +difficulty +" ausgewählt.");
        System.out.println(" ");
    }

    public static void difficultySettings() {
        switch (difficulty) {
            case AALGLATT -> {
                monsterHpMod = 0.8;
                monsterDmgMod = 0.7;
                goldMod = 1.3;
                expMod = 1.2;
                //healMod = 1.5;
                //shopInterval = 6;
            }
            case NORMAAL -> {
                monsterHpMod = 1.0;
                monsterDmgMod = 1.0;
                goldMod = 1.0;
                expMod = 1.0;
                //healMod = 1.0;
                //shopInterval = 8;
            }
            case BRUTAAL -> {
                monsterHpMod = 1.3;
                monsterDmgMod = 1.4;
                goldMod = 0.8;
                expMod = 0.9;
                //healMod = 0.7;
                //shopInterval = 10;
            }
            case QUAALVOLL -> {
                monsterHpMod = 1.7;
                monsterDmgMod = 1.8;
                goldMod = 0.6;
                expMod = 0.7;
                //healMod = 0.5;
                //shopInterval = 12;
            }
        }

    }
}
