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
    public int playerTaaler = 0;
    public int playerKilledMonsters = 0;
    public int playerEnteredRooms = 0;
    public int playerAbilityElectricEel = 0;
    public int playerAbilityLightningAura = 0;

    public player() {}


//Load & Save
    public void savegame(int Taaler) {
        try {
            Files.writeString(Path.of("savegame.txt"), String.valueOf(Taaler));
            System.out.println("Deine Taaler wurden gespeichert!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadgame() {
        int Taaler = 0;
        try {
            Taaler = Integer.parseInt(Files.readString(Path.of("savegame.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Taaler;
    }
}