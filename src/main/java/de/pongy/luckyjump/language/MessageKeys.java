package de.pongy.luckyjump.language;

public enum MessageKeys {

    MINIMUM_PLAYERS_INGAME("minimum_players_ingame"),
    PLAYER_JOIN("player_join_message"),
    PLAYER_LEFT("player_left_message"),
    PLAYER_LEFT_RUNNING_GAME("player_left_running_game"),
    GAME_FORCESTARTED("game_forcestarted"),
    STARTTIME_TOGGLED("starttime_toggled"),
    STATS_NOT_FOUND("stats_not_found"),
    GAME_STARTED("game_started"),
    GOOD_LUCK("good_luck"),
    CHECKPOINT_REACHED("checkpoint_reached"),
    COINS_RECEIVED("coins_received"),
    GAME_WON("game_won"),
    GAME_CANCELED_COUNTDOWN("game_canceled_countdown");

    private String key;

    MessageKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
