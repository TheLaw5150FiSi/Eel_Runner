package Game;

public class player {



    // Player Variablen
    public int playerCurrentLife = 100;
    public double playerLiveMod =1;
    public int playerMaxLife = 100;
    public int playerMaxMana = 0;
    public double playerManaMod =1;
    public int playerCurrentMana = 0;
    public String playerClass = "Ur-Aal";
    public int playerCurrentExp = 0;
    public int playerNeededExp = 100;
    public int playerReachedExp = 0;
    public int playerWeaponAtk = 0;
    public int playerDamage = 0;
    public int playerGold = 0;
    public int playerKilledMonsters = 0;
    public int playerEnteredRooms = 0;
    public int playerAbilityElectricEel = 0;
    public int playerAbilityLightningAura = 0;
    public int playerLevel = 1;
    public int playerStrength = 0;
    public double playerStrengthMod = 1;
    public int playerStamina = 0;
    public double playerStaminaMod = 1;
    public int playerIntelligence = 0;
    public double playerIntelligenceMod = 1;
    public int playerDefense = 0;
    public double playerDefenseMod = 1;
    public int playerCritChance = 0;
    public int playerCritDamage = 0;

    public player() {
    }


    //Load & Save
    public void saveAll(String eelsave) {
        try {
            String data = playerLevel + "|" +
                    playerCurrentLife + "|" +
                    playerMaxLife + "|" +
                    playerCurrentExp + "|" +
                    playerNeededExp + "|" +
                    playerReachedExp + "|" +
                    playerStrength + "|" +
                    playerDefense + "|" +
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
            playerStrength = Integer.parseInt(parts[6]);
            playerDefense = Integer.parseInt(parts[7]);
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

    public void playerClasses() {
        gameState gs = gameState.getInstance();

        if (playerLevel == 15) {
            System.out.println("Du hast Level 15 erreicht, Zeit eine neue Klasse zu wählen: ");
            System.out.println("1. Reißzahn-Aal (Kämpfer, viel Schaden)");
            System.out.println("2. Gewitteraal (Magier, viel Mana");
            System.out.println("3. Schattenaal (Assassine, Crit und Gift");
            System.out.println("4. Eisenflosse (Tank, Kontrolle und Defensive");
            gs.input = gs.entry.nextLine().toLowerCase();
            if (gs.input.equals("1") || gs.input.equals("reißzahn-aal")) {
                playerClass = "Reißzahn-Aal";
                playerMaxLife = 200;
                playerLiveMod = 1;
                playerMaxMana = 30;
                playerManaMod = 1;
                playerStrength = 0;
                playerStrengthMod = 1;
                playerIntelligence = 0;
                playerIntelligenceMod = 1;
                playerStamina = 0;
                playerStaminaMod = 1;
                playerCritChance = 0;
                playerCritDamage = 0;
            } else if (gs.input.equals("2") || gs.input.equals("gewitteraal")) {
                playerClass = "Gewitteraal";
                playerMaxLife = 200;
                playerLiveMod = 1;
                playerMaxMana = 30;
                playerManaMod = 1;
                playerStrength = 0;
                playerStrengthMod = 1;
                playerIntelligence = 0;
                playerIntelligenceMod = 1;
                playerStamina = 0;
                playerStaminaMod = 1;
                playerCritChance = 0;
                playerCritDamage = 0;
            } else if (gs.input.equals("3") || gs.input.equals("Schattenaal")) {
                playerClass = "Schattenaal";
                playerMaxLife = 200;
                playerLiveMod = 1;
                playerMaxMana = 30;
                playerManaMod = 1;
                playerStrength = 0;
                playerStrengthMod = 1;
                playerIntelligence = 0;
                playerIntelligenceMod = 1;
                playerStamina = 0;
                playerStaminaMod = 1;
                playerCritChance = 0;
                playerCritDamage = 0;
            } else if (gs.input.equals("4") || gs.input.equals("Eisenflosse")) {
                playerClass = "Eisenflosse";
                playerMaxLife = 300;
                playerLiveMod = 1;
                playerMaxMana = 30;
                playerManaMod = 1;
                playerStrength = 0;
                playerStrengthMod = 1;
                playerIntelligence = 0;
                playerIntelligenceMod = 1;
                playerStamina = 0;
                playerStaminaMod = 1;
                playerCritChance = 0;
                playerCritDamage = 0;
            } else {
                System.out.println("Bitte gültigen Wert eingeben!");
            }
        }
    }
}