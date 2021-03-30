package de.pongy.luckyjump.stats;

public enum StatsCategory {

    GAMES("games"),
    WINS("wins"),
    JUMPS("jumps"),
    CHECKPOINTSHIT("checkpointsHit"),
    FELLOFF("fellOff");

    private String sqlName;

    private StatsCategory(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSqlName() {
        return sqlName;
    }

}
