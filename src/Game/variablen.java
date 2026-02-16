package Game;

import java.util.Random;
import java.util.Scanner;

public class variablen {

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
    static int monsterTaalerDrop = 0;
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
    static int playerTaaler = 0;
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

    //Schwierigkei Variablen
    enum Difficulty {
        AALGLATT,
        NORMAAL,
        BRUTAAL,
        QUAALVOLL
    }

    static variablen.Difficulty difficulty = variablen.Difficulty.NORMAAL;
    static double monsterHpMod = 1.0;
    static double monsterDmgMod = 1.0;
    static double goldMod = 1.0;
    static double expMod = 1.0;
    static double healMod = 1.0;
    static int shopInterval = 8;
}
