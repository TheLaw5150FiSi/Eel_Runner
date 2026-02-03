package Game;

import java.util.Random;
import java.util.Scanner;

public class main {

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

    public static void main(String[] args) {

        startSequenz();

        //Länge des Spiels
        for (position = 0; position < dungeon.length; position++) {
            System.out.print("Gib rechts, links, hoch oder runter ein um dich zu bewegen: ");
            input = entry.nextLine().toLowerCase();
            if (input.equals("!status")) {
                statusMessage();
                continue;

            } else if (input.equals("rechts")) {
                roomNumber = rand.nextInt(0, 6);
            } else if (input.equals("links")) {
                roomNumber = rand.nextInt(0, 6);
            } else if (input.equals("hoch")) {
                roomNumber = rand.nextInt(0, 6);
            } else if (input.equals("runter")) {
                roomNumber = rand.nextInt(0, 6);
            } else {
                position--;
                System.out.println("Bitte gültigen Wert eingeben!");
                continue;
            }

            if (playerCurrentLife <= 0) {
                break;
            }
            playerEnteredRooms++;
            System.out.println(" ");
            System.out.println("Raum " + (position + 1) + " betreten.");

            //Raum 1: leer
            if (roomNumber == 0) {
                System.out.println("Der Raum ist leer!");
                System.out.println(" ");

                //Raum 2: Schatz
            } else if (roomNumber == 1) {
                playerGold = playerGold + 20;
                playerCurrentExp += 10;
                playerReachedExp += 10;
                System.out.println("Du hast einen Schatzgefunden! +20 Gold!");
                System.out.println("Du erhältst 10 Erfahrung!");
                System.out.println(" ");

                //Raum 3: Falle
            } else if (roomNumber == 2) {
                playerCurrentLife = playerCurrentLife - 5;
                playerCurrentExp += 5;
                playerReachedExp += 5;
                System.out.println("Du bist in eine Falle getreten! Du verlierst 5 Leben!");
                System.out.println("Du erhältst 5 Erfahrung!");
                System.out.println(" ");

                //Raum 4: Heiltrank
            } else if (roomNumber == 3) {
                healEel();

                //Raum 5: MonsterRaum
            } else if (roomNumber == 4) {
                getMonster();

                System.out.println("Ein wildes Monster namens " + monsterName + " erscheint!");
                System.out.print("Möchtest du kämpfen? Ja / Nein : ");
                input = entry.nextLine().toLowerCase();
                if (monsterDmgOvertime == 3) {
                    System.out.println("Steven stinkt, du verlierst 3 Leben pro Runde!");
                }
                //Kampf Abfrage ja/nein
                if (input.equals("nein")) {
                    System.out.println("Du bist geflohen!");
                } else if (input.equals("ja")) {
                    do {
                        monsterDamage = rand.nextInt(5);
                        System.out.print("Wie möchtest du angreifen? Faust / Tritt / Schwert?: ");
                        input = entry.nextLine().toLowerCase();

                        //Angriffs Art + Schadensberechnung + Drop
                        //Faustangriff
                        if (input.equals("faust")) {
                            playerAttack(10);
                            //Trittangriff
                        } else if (input.equals("tritt")) {
                            playerAttack(15);
                            //Schwertangriff
                        } else if (input.equals("schwert")) {
                            playerAttack(40);
                        } else {
                            System.out.println("Bitte gültigen Wert eingeben!");
                        }
                    } while (monsterHp > 0);
                }

                //Händler + Heiltrag 10HP
            } else if (roomNumber == 5) {
                System.out.println("Du hast einen Aalhändler gefunden, du besitzt aktuell: " + playerGold + " Gold.");
                System.out.println("Du hast im moment " + playerCurrentLife + "/" + playerMaxLife + " Leben.");
                System.out.print("Möchtest du einen großen Heilaal für 15 Gold (+10 Leben) kaufen? Ja/Nein: ");
                input = entry.nextLine().toLowerCase();
                if (input.equals("ja")) {
                    if (playerGold >= 15) {
                        playerGold -= 15;
                        if (playerMaxLife - playerCurrentLife <= 10) {
                            playerCurrentLife = playerMaxLife;
                            System.out.println("Du hast einen großen Heilaal gekauft und hast jetzt " + playerCurrentLife + " Leben");
                        } else if (playerMaxLife - playerCurrentLife > 10) {
                            playerCurrentLife += 10;
                            System.out.println("Du hast einen großer Heilaal gekauft und hast jetzt " + playerCurrentLife + " Leben");
                        }
                    } else if (playerGold < 15) {
                        System.out.println("Du hast nicht genug Gold!");
                    }
                } else if (input.equals("nein")) {
                    System.out.println("Du hast nichts gekauft!");
                    System.out.println(" ");
                    continue;
                } else {
                    System.out.println("Bitte einen gültigen Wert eingeben!");
                }
            }
            //Levelkontrolle
            if (playerCurrentExp >= playerNeededExp) {
                levelControl();
            } else {
                continue;
            }

            //Kritisches Leben Meldung
            if (playerCurrentLife < 30 && playerCurrentLife > 0) {
                System.out.println("Achtung! Deine Lebenspunkte sind kritisch!");
                System.out.println(" ");
            }

            //Ende (Tot)
            if (playerCurrentLife <= 0) {
                System.out.println("Du bist gestorben!");
                playerDead = true;
                deathRoom = position;
                break;
            }
        }
        printGameResult(playerEnteredRooms, playerCurrentLife, playerGold, playerKilledMonsters, deathRoom, playerLevel, playerReachedExp, playerDead);
    }

    //Endausgabe
    public static void printGameResult(int enteredRooms, int life, int gold, int beatMonster, int deathRoom, int level, int reachedExp, boolean dead) {

        System.out.println(" ");
        System.out.println("-----SPIELENDE-----");
        System.out.println("Erreichtes Spielerlevel: " + level);
        System.out.println("Gesamt erhaltene Erfahrung: " + reachedExp);
        System.out.println("Betretene Räume: " + enteredRooms);
        System.out.println("Verbleibendes Leben: " + life);
        System.out.println("Gesammeltes Gold: " + gold);
        System.out.println("Besiegte Monster: " + beatMonster);

        //Zusatzausgabe 100+ Gold
        if (gold >= 100) {
            System.out.println("BONUS! Du bist reich geworden!");
        }

        //Zusatzausgabe 7+ Kills
        if (beatMonster > 7) {
            System.out.println("Du bist ein wahrer Kämpfer!");
        }

        //Ausgabe Todesraum / Spiel geschafft
        if (dead == true) {
            System.out.println("Du bist im Raum " + deathRoom + " gestorben!");
        } else {
            System.out.println("Du hast den Dungeon überlebt!");
        }
    }
    //Startsequenz
    public static void startSequenz() {
        for (int i = 0; i < headline.length(); i++) {
            System.out.printf(ANSI_GREEN + headline.charAt(i) + ANSI_RESET);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(" ");
        System.out.println("Willkommen bei eel runner! - Kämpfe dich als Aal durch eine Welt voller Einhörner, Regenwürmer und anderen wilden Getier! ");
        System.out.println("Der lange Aal schlackert im Nebel der Schlacht!");
        System.out.println("Um deine Stats zu sehen, tippe !Status ein.");
        System.out.println(" ");
    }
    // Monster optionen
    public static void getMonster() {
        monsterRandom = rand.nextInt(1, 101);
        if (monsterRandom <= 35) {
            monsterName = "Steven";
            monsterGoldDrop = 5 + (playerLevel * 2);
            monsterDamage = 5 + (playerLevel * 2);
            monsterDmgOvertime = 3;
            monsterExp = 5 + (playerLevel * 2);
            monsterHp = 48 + (playerLevel * 2);
        } else if (monsterRandom <= 70) {
            monsterName = "Chris";
            monsterGoldDrop = 3 + (playerLevel * 2);
            monsterDamage = 3 + (playerLevel * 2);
            monsterExp = 1 + (playerLevel * 2);
            monsterHp = 28 + (playerLevel * 2);
        } else if (monsterRandom <= 90) {
            monsterName = "Elenaal";
            monsterGoldDrop = rand.nextInt(3, 23) + (playerLevel * 2);
            monsterDamage = rand.nextInt(3, 13) + (playerLevel * 2);
            monsterExp = 13 + (playerLevel * 2);
            monsterHp = 98 + (playerLevel * 2);
        } else if (monsterRandom <= 99) {
            monsterName = "Velcast";
            monsterGoldDrop = rand.nextInt(8, 48) + (playerLevel * 2);
            monsterDamage = rand.nextInt(3, 28) + (playerLevel * 2);
            monsterExp = 56 + (playerLevel * 2);
            monsterHp = 248 + (playerLevel * 5);
        } else if (monsterRandom == 100) {
            monsterName = "Kadir-'n'-Aal";
            monsterGoldDrop = rand.nextInt(50, 500) + (playerLevel * 2);
            monsterDamage = rand.nextInt(1, 150) + (playerLevel * 2);
            monsterDmgOvertime = 15;
            monsterExp = 98 + (playerLevel * 2);
            monsterHp = 398 + (playerLevel * 2);
    }
    //Angriffe
    public static void playerAttack (int playerAttackStr)  {
        playerDamage = rand.nextInt(1, playerAttackStr) + playerStr;
        monsterHp -= playerDamage;
        playerCurrentLife -= monsterDamage;
        playerCurrentLife -= monsterDmgOvertime;
        System.out.println("Du hast dem Monster " + playerDamage + " zugefügt! ");
        System.out.println("Das Monster fügt dir " + monsterDamage + " Schaden zu!");
        if (monsterHp <= 0) {
            playerGold += monsterGoldDrop;
            playerCurrentExp += monsterExp;
            playerReachedExp += monsterExp;
            System.out.println("Du hast das Monster besiegt! Es wurde " + monsterGoldDrop + " Gold gedroped!");
        }

    }
    //Level Kontrolle
    public static void levelControl(){
        playerCurrentExp = 0;
        playerNeededExp += 25;
        playerMaxLife += 5;
        playerStr += 2;
        playerLevel++;
        System.out.println("Level up! Du bist jetzt Level " + playerLevel + " !");
        System.out.println("Dein maximales Leben hat sich auf " + playerMaxLife + " erhöht!");
        System.out.println("Dein Stärkewert hat sich auf " + (10 + playerStr) + " erhöht");
    }
    //Statusausgabe
    public static void statusMessage() {
        System.out.println("-----STATUS-----");
        System.out.println("Spielerlevel: " + playerLevel);
        System.out.println("Erfahrung: " + playerCurrentExp + "/" + playerNeededExp);
        System.out.println("Leben: " + playerCurrentLife + "/" + playerMaxLife);
        System.out.println("Stärke: " + (playerStr + playerWeaponAtk + 10));
        System.out.println("Rüstung: " + (playerDef + 10));
        System.out.println("Gold: " + playerGold);
        System.out.println("Kills : " + playerKilledMonsters);
        System.out.println("Überlebte Räume: " + position);
        System.out.println(" ");
        position--;
    }
    //Raum Heilung
    public static void healEel() {
        if (playerMaxLife - playerCurrentLife <= 5) {
            playerCurrentLife = playerMaxLife;
            System.out.println("Du hast einen mini Heilaal gefunden und hast dein maximales Leben aufgefüllt!");
            System.out.println("Du hast jetzt " + playerCurrentLife + " Leben!");
            System.out.println(" ");
        } else if (playerMaxLife - playerCurrentLife > 5) {
            playerCurrentLife += 5;
            System.out.println("Du hast einen mini-Heilaal gefunden und bekommst 5 Leben dazu!");
            System.out.println("Du hast jetzt " + playerCurrentLife + " Leben!");
            System.out.println(" ");
        }
    }



}

