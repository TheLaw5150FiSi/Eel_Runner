package Game;

import java.util.Random;
import java.util.Scanner;

public class gameState {
    private static gameState instance;

    // Variablen
    public int[] dungeon = new int[30];
    public int deathRoom = -1;
    public int roomNumber = 0;
    public String input;
    public int position;
    public int monsterGoldDrop;
    public int monsterDamage;
    public int monsterHp;
    public int monsterExp;
    public int monsterRandom;
    public int monsterDmgOvertime = 0;
    public String monsterName;
    public int itemAal38 = 1;
    public boolean playerDead = false;
    public int delay = 5;
    public Random rand = new Random();
    public Scanner entry = new Scanner(System.in);

    // Schwierigkeit
    public enum Difficulty {
        AALGLATT, NORMAAL, BRUTAAL, QUAALVOLL
    }
    public Difficulty difficulty = Difficulty.NORMAAL;
    public double monsterHpMod = 1.0;
    public double monsterDmgMod = 1.0;
    public double goldMod = 1.0;
    public double expMod = 1.0;
    public double healMod = 1.0;
    public int shopInterval = 8;

    public static gameState getInstance() {
        if (instance == null)
            instance = new gameState();

        return instance;
    }

    private gameState() {}

    //Monster Typ
    public void getMonster(int playerLevel) {
        monsterRandom = rand.nextInt(1, 20);
        int extraStuff = playerLevel * 2;
        if (monsterRandom <= 35) {
            monsterName = "Steven";
            monsterGoldDrop = (int) ((5 + extraStuff) * goldMod);
            monsterDamage = (int) ((5 + extraStuff) * monsterDmgMod);
            monsterDmgOvertime = 3;
            monsterExp = (int) ((5 + extraStuff) * expMod);
            monsterHp = (int) ((48 + extraStuff)* monsterHpMod);
        } else if (monsterRandom <= 70) {
            monsterName = "Chris";
            monsterGoldDrop = (int) ((3 + extraStuff) * goldMod);
            monsterDamage = (int) ((3 + extraStuff) * monsterDmgMod);
            monsterExp = (int) ((1 + extraStuff) * expMod);
            monsterHp = (int) ((28 + extraStuff) * monsterHpMod);
        } else if (monsterRandom <= 90) {
            monsterName = "Elenaal";
            monsterGoldDrop = (int) ((rand.nextInt(3, 23) + extraStuff) * goldMod);
            monsterDamage = (int) ((rand.nextInt(3, 13) + extraStuff) * monsterDmgMod);
            monsterExp = (int) ((13 + extraStuff) * expMod);
            monsterHp = (int) ((98 + extraStuff) * monsterHpMod);
        } else if (monsterRandom <= 99) {
            monsterName = "Velcast";
            monsterGoldDrop = (int) ((rand.nextInt(8, 48) + extraStuff) * goldMod);
            monsterDamage = (int) ((rand.nextInt(3, 28) + extraStuff) * monsterDmgMod);
            monsterExp = (int) ((56 + extraStuff) * expMod);
            monsterHp = (int) ((248 + (playerLevel * 5)) * monsterHpMod);
        } else if (monsterRandom == 100) {
            monsterName = "Anaal";
            monsterGoldDrop = (int) ((rand.nextInt(50, 500) + extraStuff) * goldMod);
            monsterDamage = (int) ((rand.nextInt(1, 150) + extraStuff) * monsterDmgMod);
            monsterDmgOvertime = 15;
            monsterExp = (int) ((98 + extraStuff) * expMod);
            monsterHp = (int) ((398 + extraStuff) * monsterHpMod);
        }
    }

    //Schwierigkeit Einstellungen
    public void difficultySettings() {
        switch (difficulty) {
            case AALGLATT -> {
                monsterHpMod = 0.8;
                monsterDmgMod = 0.7;
                goldMod = 1.3;
                expMod = 1.2;
            }
            case NORMAAL -> {
                monsterHpMod = 1.0;
                monsterDmgMod = 1.0;
                goldMod = 1.0;
                expMod = 1.0;
            }
            case BRUTAAL -> {
                monsterHpMod = 1.3;
                monsterDmgMod = 1.4;
                goldMod = 0.8;
                expMod = 0.9;
            }
            case QUAALVOLL -> {
                monsterHpMod = 1.7;
                monsterDmgMod = 1.8;
                goldMod = 0.6;
                expMod = 0.7;
            }
        }
    }

    public void saveAll(String eelsave) {
        try {
            String data = position + "|" +
                    deathRoom + "|" +
                    roomNumber + "|" +
                    itemAal38 + "|" +
                    difficulty.name();

            java.nio.file.Files.writeString(java.nio.file.Path.of("gs_" + eelsave), data);
            System.out.println("GameState gespeichert!");
        } catch (Exception e) {
            System.out.println("GameState Save Fehler!");
        }
    }

    public void loadAll(String eelsave) {
        try {
            String data = java.nio.file.Files.readString(java.nio.file.Path.of("gs_" + eelsave));
            String[] parts = data.split("\\|");

            position = Integer.parseInt(parts[0]);
            deathRoom = Integer.parseInt(parts[1]);
            roomNumber = Integer.parseInt(parts[2]);
            itemAal38 = Integer.parseInt(parts[3]);
            difficulty = Difficulty.valueOf(parts[4]);
            difficultySettings();

            System.out.println("GameState geladen: Raum " + position);
        } catch (Exception e) {
            System.out.println("Kein GameState gefunden!");
        }
    }

    public void scannerEntry() {

    }

}