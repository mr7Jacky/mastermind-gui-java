package hw01.game;

/**
 * A enumerator represent for 6 kind of tokens
 * including blue, green, orange, purple, red, yellow
 * @author Jacky Lin
 */
public enum Token {
    /** blue, green, orange, purple, red, yellow tokens */
    B("1"),G("2"),O("3"),P("4"),R("5"),Y("6");
    /** name for tokens */
    private String num;
    /**
     * constructor for the number of the token
     * @author Jacky Lin
     */
    Token(String num) {
        this.num = num;
    }
    /**
     * Get method for tokens
     * @author Jacky Lin
     */
    public String getNum() {
        return num;
    }

}
