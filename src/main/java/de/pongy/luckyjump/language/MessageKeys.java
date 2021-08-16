package de.pongy.luckyjump.language;

public enum MessageKeys {

    MINIMUM_PLAYERS_INGAME("minimum_players_ingame"),
    PLAYER_JOIN("player_join_message"),
    PLAYER_LEFT("player_left_message"),
    GAME_FORCESTARTED("game_forcestarted"),
    STARTTIME_TOGGLED("starttime_toggled"),
    STATS_NOT_FOUND("stats_not_found");

    private String key;

    private MessageKeys(String key) {

    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
