package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;

import java.util.Formatter;

/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size with no pieces
     *  and score 0. */
    // 构造函数
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }
    // 含值构造函数
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    // 存在一个null则返回true
    public boolean emptySpaceExists() {
        int size = board.size();

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(board.tile(i, j) == null)
                {
                    return true;
                }
            }
        }

        return false;
    }

    // 获胜则返回true
    public boolean maxTileExists() {
        int size = board.size();

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if((board.tile(i, j) != null) && (board.tile(i, j).value() == MAX_PIECE))
                {
                    return true;
                }
            }
        }
        return false;
    }

    // 检查有无可合并项
    private boolean isMergeable(){
        int size = board.size();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if((i - 1 >= 0 && board.tile(i - 1, j).value() == board.tile(i, j).value()) ||
                        (i + 1 < size && board.tile(i + 1, j).value() == board.tile(i, j).value()) ||
                        (j - 1 >= 0 && board.tile(i, j).value() == board.tile(i, j - 1).value()) ||
                        (j + 1 < size && board.tile(i, j).value() == board.tile(i, j + 1).value())) {
                    return true;
                }
            }
        }
        return false;
    }
    // 至少存在一个可行移动
    public boolean atLeastOneMoveExists() {
        if(emptySpaceExists()){
            return true;
        }else{
            if(isMergeable()){
                return true;
            }else{
                return false;
            }
        }
    }

    // 将单个方块向上移动到指定位置（包括合并）
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);
        int myValue = currTile.value();
        int targetY = y;

        while(true){
            targetY++;
            if(targetY < board.size() && board.tile(x, targetY) == null){
                continue;
            }else{
                if(targetY < board.size() && board.tile(x, targetY).value() == myValue &&
                        !board.tile(x, targetY).wasMerged()){
                    score += myValue * 2;
                    break;
                }
                targetY--;
                break;
            }
        }

        if(targetY == y){
            return;
        }else {
            board.move(x, targetY, currTile);
        }
    }

    // 将整行向上移动到指定位置（包括合并）
    public void tiltColumn(int x) {
        for(int y = board.size() - 1; y >= 0; y--){ // 自上而下移动方块
            if(board.tile(x, y) != null) {
                moveTileUpAsFarAsPossible(x, y);
            }
        }
    }

    // 将整个board向上移动
    public void tilt(Side side) {
        for(int x = 0; x < board.size(); x++){
            tiltColumn(x);
        }
    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        board.setViewingPerspective(side);
        tilt(side);
        board.setViewingPerspective(Side.NORTH);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
