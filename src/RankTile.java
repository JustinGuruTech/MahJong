public class RankTile extends Tile {
    protected int rank;

    public RankTile(int rank) {
        this.rank = rank;
    }

    public boolean matches(Tile other) {

        // same class
        if (super.matches(other)) {

            // return true only if equal ranks
            return this.rank == ((RankTile)other).rank;
        }

        return false;
    }
}
