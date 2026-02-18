package Game;

public class main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String headline = """
            ███████╗███████╗██╗         ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗███████╗██████╗
            ██╔════╝██╔════╝██║         ██╔══██╗██║   ██║████╗  ██║████╗  ██║██╔════╝██╔══██
            █████╗  █████╗  ██║         ██████╔╝██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝
            ██╔══╝  ██╔══╝  ██║         ██╔══██╗██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗
            ███████╗███████╗███████╗    ██║  ██║╚██████╔╝██║ ╚████║██║ ╚████║███████╗██║  ██║
            ╚══════╝╚══════╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═
            """;

    public static String headline2 = """
    Schwierigkeit: %s
    Klasse: %s
    Level: %-3d  EXP: %-4d/%-4d
    Leben: %4d /%-4d Mana: %4d/%-4d
    Stärke: %-3d Stamina: %-3d
    Intelligenz: %d
    Critchance: %-3d  Critdamage: %-3d
    Aal38: %d
    Gold: %d
    Kills: %-4d  Räume: %-3d
    """;
    private static void setupHeadline2(player p, gameState gs) {
        headline2 = headline2.formatted(
                gs.difficulty,
                p.playerClass,
                p.playerLevel, p.playerCurrentExp, p.playerNeededExp,
                p.playerCurrentLife, p.playerMaxLife, p.playerCurrentMana, p.playerMaxMana,
                p.playerStrength,
                p.playerStamina,
                p.playerIntelligence,
                p.playerCritChance,
                p.playerCritDamage,
                gs.itemAal38,
                p.playerGold,
                p.playerKilledMonsters,
                gs.position
        );
    }


    public static void main(String[] args) {
        gameState gs = gameState.getInstance();
        player p = new player();

        startSequenz(p, gs);

        for (gs.position = 0; gs.position < gs.dungeon.length; gs.position++) {
            System.out.print("Gib rechts, links, hoch oder runter ein um dich zu bewegen: ");
            gs.input = gs.entry.nextLine().toLowerCase();

            if (gs.input.equals("!status")) {
                statusMessage(p, gs);
                gs.position--;
            } else if (gs.input.equals("!save")) {
                p.saveAll("eelrunner.save");
                gs.saveAll("eelrunner.save");
                System.out.println("💾 Spiel gespeichert!");
                gs.position--;
            } else if (gs.input.equals("!load")) {
                p.loadAll("eelrunner.save");
                gs.loadAll("eelrunner.save");
                System.out.println("💾 Spiel geladen!");
                gs.position--;
            } else if (gs.input.equals("rechts") || gs.input.equals("links")
                    || gs.input.equals("hoch") || gs.input.equals("runter")) {
                gs.roomNumber = gs.rand.nextInt(1, 6);
            } else {
                gs.position--;
                System.out.println("Bitte gültigen Wert eingeben!");
                continue;
            }

            if (p.playerCurrentLife <= 0) break;
            p.playerEnteredRooms++;
            System.out.println(" ");
            System.out.println("Raum " + (gs.position + 1) + " betreten.");

            if (gs.roomNumber == 0) {
                System.out.println("Der Raum ist leer!");
                System.out.println(" ");
            } else if (gs.roomNumber == 1) {
                p.playerGold += 20;
                p.playerCurrentExp += 10;
                p.playerReachedExp += 10;
                p.saveAll("quick.save");
                System.out.println("Du hast einen Schatzgefunden! +20 Gold!");
                System.out.println("Du erhältst 10 Erfahrung!");
                System.out.println(" ");
            } else if (gs.roomNumber == 2) {
                p.playerCurrentLife -= 5;
                p.playerCurrentExp += 5;
                p.playerReachedExp += 5;
                System.out.println("Du bist in eine Falle getreten! Du verlierst 5 Leben!");
                System.out.println("Du erhältst 5 Erfahrung!");
                System.out.println(" ");
            } else if (gs.roomNumber == 3) {
                healEel(p);
            } else if (gs.roomNumber == 4) {
                gs.getMonster(p.playerLevel);
                System.out.println("Ein wildes Monster namens " + gs.monsterName + " erscheint!");
                System.out.print("Möchtest du kämpfen? Ja / Nein : ");
                gs.input = gs.entry.nextLine().toLowerCase();

                if (gs.monsterDmgOvertime == 3) {
                    System.out.println("Steven stinkt, du verlierst 3 Leben pro Runde!");
                }
                if (gs.input.equals("nein")) {
                    System.out.println("Du bist geflohen!");
                } else if (gs.input.equals("ja")) {
                    do {
                        System.out.println("Wie möchtest du angreifen? Faust / Tritt / Schwert?");
                        System.out.println("Oder möchtest du fliehen (Aal38)?");
                        System.out.print("Eingabe: ");
                        gs.input = gs.entry.nextLine().toLowerCase();

                        if (gs.input.equals("faust")) {
                            playerAttack(p, gs, 10);
                        } else if (gs.input.equals("tritt")) {
                            playerAttack(p, gs, 15);
                        } else if (gs.input.equals("schwert")) {
                            playerAttack(p, gs, 40);
                        } else if (gs.input.equals("aal38")) {
                            if (gs.itemAal38 > 0) {
                                gs.itemAal38--;
                                System.out.println("Du bist geflohen!");
                                System.out.println("");
                                break;
                            } else {
                                System.out.println("Du hast keinen Passierschein Aal38!");
                                System.out.println("");
                            }
                        } else {
                            System.out.println("Bitte gültigen Wert eingeben!");
                            System.out.println("");
                        }
                    } while (gs.monsterHp > 0);
                }
            } else if (gs.roomNumber == 5) {
                ShopDealer(p, gs);
            }

            if (p.playerCurrentExp >= p.playerNeededExp) {
                levelControl(p);
            }

            if (p.playerCurrentLife < 30 && p.playerCurrentLife > 0) {
                System.out.println("Achtung! Deine Lebenspunkte sind kritisch!");
                System.out.println(" ");
            }

            if (p.playerCurrentLife <= 0) {
                System.out.println("Du bist gestorben!");
                gs.playerDead = true;
                gs.deathRoom = gs.position;
                break;
            }
        }
        printGameResult(p.playerEnteredRooms, p.playerCurrentLife, p.playerGold, p.playerKilledMonsters, gs.deathRoom, p.playerLevel, p.playerReachedExp, gs.playerDead);
    }

    // Methoden
    //Ausgabe bei beenden des Spiels
    public static void printGameResult(int enteredRooms, int life, int gold, int beatMonster, int deathRoom, int level, int reachedExp, boolean dead) {
        System.out.println(" ");
        System.out.println("-----SPIELENDE-----");
        System.out.println("Erreichtes Spielerlevel: " + level);
        System.out.println("Gesamt erhaltene Erfahrung: " + reachedExp);
        System.out.println("Verbleibendes Leben: " + life);
        System.out.println("Gesammeltes Gold: " + gold);
        System.out.println("Besiegte Monster: " + beatMonster);
        if (gold >= 100) System.out.println("BONUS! Du bist reich geworden!");
        if (beatMonster > 7) System.out.println("Du bist ein wahrer Kämpfer!");
        if (dead) System.out.println("Du bist im Raum " + enteredRooms + " gestorben!");
        else System.out.println("Du hast den Dungeon überlebt!");
    }

    //Vollständige Start Sequenz
    public static void startSequenz(player p, gameState gs) {
        for (int i = 0; i < headline.length(); i++) {
            System.out.printf(ANSI_GREEN + headline.charAt(i) + ANSI_RESET);
            try {
                Thread.sleep(gs.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(" ");
        System.out.println("Willkommen bei eel runner! - Kämpfe dich als Aal durch eine Welt voller Einhörner, Regenwürmer und anderen wilden Getier!");
        System.out.println("Der lange Aal schlackert im Nebel der Schlacht!");
        System.out.println("Um deine Stats zu sehen, tippe !Status ein.");
        System.out.println("Auf welcher Schwierigkeitsstufe möchtest du spielen?");
        System.out.println("1. Aalglatt 2. NormAal 3. BrutAal 4. QuAalvoll");
        System.out.print("Eingabe: ");
        gs.input = gs.entry.nextLine().toLowerCase();

        switch (gs.input) {
            case "aalglatt" -> gs.difficulty = gameState.Difficulty.AALGLATT;
            case "brutaal" -> gs.difficulty = gameState.Difficulty.BRUTAAL;
            case "quaalvoll" -> gs.difficulty = gameState.Difficulty.QUAALVOLL;
            default -> gs.difficulty = gameState.Difficulty.NORMAAL;
        }
        gs.difficultySettings();
        System.out.println("Du hast die Schwierigkeit " + gs.difficulty + " ausgewählt.");
        System.out.println(" ");
    }

    //Statusüberprüfung !status
    public static void statusMessage(player p, gameState gs) {
        setupHeadline2(p, gs);
        System.out.println(headline2);
    }

    //Angriffsschema
    public static void playerAttack(player p, gameState gs, int playerAttackStr) {
        p.playerDamage = gs.rand.nextInt(1, playerAttackStr) + p.playerStrength;
        gs.monsterHp -= p.playerDamage;
        p.playerCurrentLife -= gs.monsterDamage;
        p.playerCurrentLife -= gs.monsterDmgOvertime;
        System.out.println("Du hast dem Monster " + p.playerDamage + " zugefügt!");
        System.out.println("Das Monster fügt dir " + gs.monsterDamage + " Schaden zu!");
        if (gs.monsterHp <= 0) {
            p.playerGold += gs.monsterGoldDrop;
            p.playerCurrentExp += gs.monsterExp;
            p.playerReachedExp += gs.monsterExp;
            p.saveAll("eelrunner.save");
            System.out.println("Du hast das Monster besiegt! +" + gs.monsterGoldDrop + " Gold!");
            p.playerKilledMonsters++;
        }
    }

    //Level System
    public static void levelControl(player p) {
        p.playerCurrentExp = 0;
        p.playerNeededExp += 25;
        p.playerMaxLife += 5;
        p.playerStrength += 2;
        p.playerLevel++;
        System.out.println("Level up! Du bist jetzt Level " + p.playerLevel + "!");
        p.playerClasses();
        System.out.println(" ");
    }

    //Heilungs Item Random Raum
    public static void healEel(player p) {
        if (p.playerMaxLife - p.playerCurrentLife <= 5) {
            p.playerCurrentLife = p.playerMaxLife;
            System.out.println("Du hast einen mini Heilaal gefunden und hast dein maximales Leben aufgefüllt!");
        } else {
            p.playerCurrentLife += 5;
            System.out.println("Du hast einen mini-Heilaal gefunden und bekommst 5 Leben dazu!");
        }
        System.out.println("Du hast jetzt " + p.playerCurrentLife + " Leben!");
        System.out.println(" ");
    }

    //Shop Händler
    public static void ShopDealer(player p, gameState gs) {
        System.out.println("Aalhändler! Gold: " + p.playerGold);
        System.out.print("Kaufen? Ja/Nein: ");
        gs.input = gs.entry.nextLine().toLowerCase();
        if (gs.input.equals("nein")) {
            System.out.println("Tschüss!");
            return;
        }

        String shop = "1.Heilaal(50G) 2.EXP(125G) 3.HPMax(275G) 4.Aal38(300G) 5.Zitteraal(275G) 6.Blitz(275G) 7.Exit";
        do {
            System.out.println(shop);
            gs.input = gs.entry.nextLine();
            if (gs.input.equals("1") && p.playerGold >= 50) {
                p.playerCurrentLife = Math.min(p.playerMaxLife, p.playerCurrentLife + 20);
                p.playerGold -= 50;
                System.out.println("+" + p.playerCurrentLife + " HP!");
            } else if (gs.input.equals("2") && p.playerGold >= 125) {
                p.playerCurrentExp += 20;
                p.playerReachedExp += 20;
                p.playerGold -= 125;
            } else if (gs.input.equals("7")) {
                break;
            }
            // Füge hier den Rest deines Shops hinzu wenn gewünscht
        } while (!gs.input.equals("7"));
    }
}
