package bg.sofia.uni.fmi.ai.tictactoe;

public enum PlayerMarker {
    EMPTY("-"),
    HUMAN("X"),
    BOT("O");

    private final String value;

    PlayerMarker(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}