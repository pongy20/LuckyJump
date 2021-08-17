package de.pongy.luckyjump.language;

public enum PlaceholderPrefabs {
    PLAYER("{player}"),
    TARGET("{target}"),
    MAX_PLAYER("{max_players}"),
    MIN_PLAYER("{min_players}"),
    CURRENT_PLAYER("{current_players}"),
    CURRENT_TIME("{current_time}"),
    COINS("{coins}");

    private String name;

    private PlaceholderPrefabs(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    public String getName() {
        return name;
    }
}
