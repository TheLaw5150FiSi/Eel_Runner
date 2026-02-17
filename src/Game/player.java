package Game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class player {

    // Player Variablen
    public int playerCurrentLife = 100;
    public int playerMaxLife = 100;
    public int playerCurrentExp = 0;
    public int playerNeededExp = 100;
    public int playerReachedExp = 0;
    public int playerLevel = 1;
    public int playerStr = 0;
    public int playerDef = 0;
    public int playerWeaponAtk = 0;
    public int playerDamage = 0;
    public int playerGold = 0;
    public int playerKilledMonsters = 0;
    public int playerEnteredRooms = 0;
    public int playerAbilityElectricEel = 0;
    public int playerAbilityLightningAura = 0;

    public player() {}


//Load & Save
    public void saveAll(String eelsave) {
        try {
            String data = playerLevel + "|" +
                    playerCurrentLife + "|" +
                    playerMaxLife + "|" +
                    playerCurrentExp + "|" +
                    playerNeededExp + "|" +
                    playerReachedExp + "|" +
                    playerStr + "|" +
                    playerDef + "|" +
                    playerGold + "|" +
                    playerKilledMonsters + "|" +
                    playerEnteredRooms + "|" +
                    playerAbilityElectricEel + "|" +
                    playerAbilityLightningAura;

            java.nio.file.Files.writeString(java.nio.file.Path.of(eelsave), data);
            System.out.println("🦈 Savegame gespeichert: " + eelsave);
        } catch (Exception e) {
            System.out.println("Save Fehler: " + e.getMessage());
        }
    }

    public void loadAll(String eelsave) {
        try {
            String data = java.nio.file.Files.readString(java.nio.file.Path.of(eelsave));
            String[] parts = data.split("\\|");

            playerLevel = Integer.parseInt(parts[0]);
            playerCurrentLife = Integer.parseInt(parts[1]);
            playerMaxLife = Integer.parseInt(parts[2]);
            playerCurrentExp = Integer.parseInt(parts[3]);
            playerNeededExp = Integer.parseInt(parts[4]);
            playerReachedExp = Integer.parseInt(parts[5]);
            playerStr = Integer.parseInt(parts[6]);
            playerDef = Integer.parseInt(parts[7]);
            playerGold = Integer.parseInt(parts[8]);
            playerKilledMonsters = Integer.parseInt(parts[9]);
            playerEnteredRooms = Integer.parseInt(parts[10]);
            playerAbilityElectricEel = Integer.parseInt(parts[11]);
            playerAbilityLightningAura = Integer.parseInt(parts[12]);

            System.out.println("🦈 Savegame geladen: Level " + playerLevel);
        } catch (Exception e) {
            System.out.println("Kein Savegame gefunden - Neues Spiel!");
        }
    }
}