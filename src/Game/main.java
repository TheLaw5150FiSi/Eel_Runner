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

    public static void main(String[] args) {

        startSequenz();

        //Länge des Spiels
        for (position = 0; position < dungeon.length; position++) {
            System.out.print("Gib rechts, links, hoch oder runter ein um dich zu bewegen: ");
            input = entry.nextLine().toLowerCase();
            if (input.equals("!status")) {
                statusMessage();
                continue;

            } else if (input.equals("rechts")
                    || input.equals("links")
                    || input.equals("hoch")
                    || input.equals("runter")) {
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
                        System.out.println("Wie möchtest du angreifen? Faust / Tritt / Schwert?");
                        System.out.println("Oder möchtest du fliehen (Aal38)?");
                        System.out.print("Eingabe: ");
                        input = entry.nextLine().toLowerCase();

                        //Angriffe + Flucht
                        if (input.equals("faust")) {
                            playerAttack(10);
                            //Trittangriff
                        } else if (input.equals("tritt")) {
                            playerAttack(15);
                            //Schwertangriff
                        } else if (input.equals("schwert")) {
                            playerAttack(40);
                        } else if (input.equals("aal38")) {
                            if (itemAal38 > 0) {
                                itemAal38--;
                                System.out.println("Du bist geflohen!");
                                System.out.println("");
                                break;
                            } else {
                                System.out.println("Du hast keinen Passierschein Aal38!");
                                System.out.println("");
                            }
                        }else {
                            System.out.println("Bitte gültigen Wert eingeben!");
                            System.out.println("");
                        }
                    } while (monsterHp > 0);
                }

                //Händler + Heiltrag 10HP
            } else if (roomNumber == 5) {
                ShopDealer();
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
        int extraStuff = playerLevel * 2;
        if (monsterRandom <= 35) {
            monsterName = "Steven";
            monsterGoldDrop = 5 + extraStuff;
            monsterDamage = 5 + extraStuff;
            monsterDmgOvertime = 3;
            monsterExp = 5 + extraStuff;
            monsterHp = 48 + extraStuff;
        } else if (monsterRandom <= 70) {
            monsterName = "Chris";
            monsterGoldDrop = 3 + extraStuff;
            monsterDamage = 3 + extraStuff;
            monsterExp = 1 + extraStuff;
            monsterHp = 28 + extraStuff;
        } else if (monsterRandom <= 90) {
            monsterName = "Elenaal";
            monsterGoldDrop = rand.nextInt(3, 23) + extraStuff;
            monsterDamage = rand.nextInt(3, 13) + extraStuff;
            monsterExp = 13 + extraStuff;
            monsterHp = 98 + extraStuff;
        } else if (monsterRandom <= 99) {
            monsterName = "Velcast";
            monsterGoldDrop = rand.nextInt(8, 48) + extraStuff;
            monsterDamage = rand.nextInt(3, 28) + extraStuff;
            monsterExp = 56 + extraStuff;
            monsterHp = 248 + (playerLevel * 5);
        } else if (monsterRandom == 100) {
            monsterName = "Anaal";
            monsterGoldDrop = rand.nextInt(50, 500) + extraStuff;
            monsterDamage = rand.nextInt(1, 150) + extraStuff;
            monsterDmgOvertime = 15;
            monsterExp = 98 + extraStuff;
            monsterHp = 398 + extraStuff;
        }
    }

    //Angriffe
    public static void playerAttack(int playerAttackStr) {
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
    public static void levelControl() {
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
        System.out.println("Zitteraal Level: " +playerAbilityElectricEel);
        System.out.println("Blitz-Aura Level: " +playerAbilityLightningAura);
        System.out.println("Passierschein Aal38: " +itemAal38);
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

    //Händler
    public static void ShopDealer() {


        System.out.println("Du hast einen Aalhändler gefunden, du besitzt aktuell: " + playerGold + " Gold.");
        System.out.println("aktuelles Leben: " + playerCurrentLife + "/" + playerMaxLife);
        System.out.println("aktuelle Stärke: " + playerStr);
        System.out.println("aktuelle Rüstung: " + playerDef);

        System.out.print("Möchtest du etwas kaufen? Ja/Nein: ");
        input = entry.nextLine().toLowerCase();
        if (input.equals("nein")) {
            System.out.println("Und tschüss");
            System.out.println("");
        } else if (input.equals("ja")) {
            String shopDisplay = """
                    
                    -----Verfügbare Artikel-----
                    1. Großer Heilaal +20hp | 50 Gold
                    2. EXP Aalexier +20 EXP | 125 Gold
                    3. HP Maximaaltrank +10hp | 275 Gold
                    4. Item: Passierschein Aal38 - ermöglicht die Flucht aus seinem Kampf | 300 Gold
                    5. Fähigkeit Zitteraal + 2 Stärke | 275 Gold
                    6. Fähigkeit Blitz-Aura + 2 Rüstung | 275 Gold
                    
                    7. Shop verlassen
                    
                    Was möchtest du kaufen?
                    Bitte die Passende Nummer eingeben: """;
            do {
                System.out.println(shopDisplay);
                input = entry.nextLine();
                if (input.equals("1")) {
                    if (playerGold < 50) {
                        System.out.println("Du hast leider nicht genug Gold!");
                        continue;
                    }
                    if (playerMaxLife - playerCurrentLife <= 20) {
                        playerCurrentLife = playerMaxLife;
                        System.out.println("Du hast einen großen Heilaal gekauft und hast jetzt " + playerCurrentLife + " Leben");
                        playerGold -= 50;
                    } else if (playerMaxLife - playerCurrentLife > 20) {
                        playerCurrentLife += 20;
                        System.out.println("Du hast einen großer Heilaal gekauft und hast jetzt " + playerCurrentLife + " Leben");
                        playerGold -= 50;
                    }
                } else if (input.equals("2")) {
                    if (playerGold < 125) {
                        System.out.println("Du hast leider nicht genug Gold!");
                    } else {
                        System.out.println("Du hast ein EXP Aalexier gekauft und erhältst 20 Erfahrung!");
                        playerCurrentExp += 20;
                        playerReachedExp += 20;
                        playerGold -= 125;
                    }
                } else if (input.equals("3")) {
                    if (playerGold < 275) {
                        System.out.println("Du hast leider nicht genug Gold!");
                    } else {
                        System.out.println("Du hast einen HP Maximaaltrank gekauft und hast jetzt " + playerCurrentLife + " / " + (playerMaxLife + 10));
                        playerCurrentLife += 10;
                        playerGold -= 275;
                    }

                } else if (input.equals("4")) {
                    if (playerGold < 300) {
                        System.out.println("Du hast leider nicht genug Gold!");
                    } else {
                        System.out.println("Du hast einen Passierschein Aal38 gekauft und hast jetzt " + itemAal38 + "Passierscheine.");
                        itemAal38++;
                        playerGold -= 300;
                    }

                } else if (input.equals("5")) {
                    if (playerGold < 275) {
                        System.out.println("Du hast leider nicht genug Gold!");
                    } else {
                        System.out.println("Du hast die Fähigkeit Zitteraal gekauft und erhältst +1 Stärke");
                        playerAbilityElectricEel++;
                        playerStr++;
                        playerGold -= 275;
                    }
                } else if (input.equals("6")) {
                    if (playerGold < 275) {
                        System.out.println("Du hast leider nicht genug Gold!");
                    } else {
                        System.out.println("Du hast die Fähigkeit Blitz-Aura gekauft und erhältst +1 Rüstung");
                        playerDef++;
                        playerAbilityLightningAura++;
                        playerGold -= 275;
                    }
                } else if (input.equals("7")) {
                    System.out.println("Du hast den Shop verlassen!");
                    System.out.println("");
                }
            } while (!input.equals("7"));
        }
    }


}