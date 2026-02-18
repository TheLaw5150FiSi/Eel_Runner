package Game;

public class testing {
    public static String headline2;

    // Player und GameState Klassen (vereinfacht)
    static class player {
        String playerClass = "Warrior";
        int playerLevel = 115;
        int playerCurrentExp = 2345;
        int playerNeededExp = 5000;
        int playerCurrentLife = 850;
        int playerMaxLife = 1000;
        int playerCurrentMana = 120;
        int playerMaxMana = 300;
        int playerStrength = 1125;
        int playerStamina = 18;
        int playerIntelligence = 12;
        int playerCritChance = 8;
        int playerCritDamage = 150;
        int playerGold = 1250;
        int playerKilledMonsters = 47;
    }

    static class gameState {
        String difficulty = "Hard";
        int itemAal38 = 1;
        int position = 12;
    }

    private static void setupHeadline2(player p, gameState gs) {
        StringBuilder sb = new StringBuilder();

        // ╔═══════╤══════════════════════╤══════════╗ 40 Char
        sb.append("╔═══════════════════════════════════╗").append("\n");
        sb.append("║         CHARAKTER STATUS          ║").append("\n");
        sb.append("╠══════════════════╦════════════════╣").append("\n");
        sb.append(String.format("║ Level            ║  %-13d ║%n", p.playerLevel));
        sb.append(String.format("║ EXP              ║  %4d/%-4d     ║%n", p.playerCurrentExp, p.playerNeededExp));
        sb.append(String.format("║ Klasse           ║  %-13s ║%n",p.playerClass));
        sb.append("╠──────────────────╬────────────────╣").append("\n");
        sb.append(String.format("║ HP               ║ %4d/%-4d      ║%n",
                p.playerCurrentLife, p.playerMaxLife));
        sb.append(String.format("║ MP               ║ %4d/%-4d      ║%n",
                p.playerCurrentMana, p.playerMaxMana));
        sb.append("╠──────────────────╬────────────────╣").append("\n");
        sb.append(String.format("║ Physkaalkraft    ║  %-13d ║%n", p.playerStrength));
        sb.append(String.format("║ Vitaalität       ║  %-13d ║%n", p.playerStamina));
        sb.append(String.format("║ Mentaalkraft     ║  %-13d ║%n", p.playerIntelligence));
        sb.append(String.format("║ Kritikaalchance  ║  %-13d ║%n", p.playerCritChance));
        sb.append(String.format("║ Kritikaalschaden ║  %-13d ║%n", p.playerCritDamage));
        sb.append("╠──────────────────╬────────────────╣").append("\n");
        sb.append(String.format("║ Aal38            ║  %-13d ║%n", gs.itemAal38));
        sb.append(String.format("║ Gold             ║  %-13d ║%n", p.playerGold));
        sb.append(String.format("║ Kills            ║  %-13d ║%n", p.playerKilledMonsters));
        sb.append(String.format("║ Räume            ║  %-13d ║%n", gs.position));
        sb.append("╚══════════════════╩════════════════╝").append("\n");

        headline2 = sb.toString();
    }

    public static void main(String[] args) {
        player p = new player();
        gameState gs = new gameState();

        setupHeadline2(p, gs);
        System.out.println(headline2);
    }
}