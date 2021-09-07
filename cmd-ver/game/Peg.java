package hw01.game;

/**
 * A enumerator represent for 3 kind of peg each indicate the status of tokens
 * CORRECT stands for the right token in the right position
 * GOODCOLOR stands for the right token not in the right position
 * WRONG stands for the wrong token
 *
 * @author Jacky Lin
 */
public enum Peg {
    /** C, U, E pegs*/
    CORRECT("*"), GOODCOLOR("+"), WRONG("_");

    /** the value of the C, U ,E pegs, for their shown priority*/
    private String name;

    /**
     * the constructor for the Peg {@link Peg} to initialized the name of pegs
     * @author Jacky Lin
     */
    Peg(String name) {
        this.name = name;
    }

    /**
     * getter method fot the value of the Pegs
     * @return the name of the token
     * @author Jacky Lin
     */
    public String getName() {
        return name;
    }
}
