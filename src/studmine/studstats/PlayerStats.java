package studmine.studstats;

public class PlayerStats
{
    private String pseudo;
    private int blocksBroken;
    private int blocksPlaced;
    private int stupidDeaths;
    private int normalDeaths;
    private int kills;
    private int timePlayed;
    private int verbosity;

    /*Increments methods*/

    public void incrementBlocksBroken()
    {
        this.blocksBroken++;
    }

    public void incrementBlocksPlaced()
    {
        this.blocksPlaced++;
    }

    public void incrementStupidDeaths()
    {
        this.stupidDeaths++;
    }

    public void incrementNormalDeaths()
    {
        this.normalDeaths++;
    }

    public void incrementKills()
    {
        this.kills++;
    }

    public void incrementTimePlayed()
    {
        this.timePlayed++;
    }

    public void incrementVerbosity()
    {
        this.verbosity++;
    }

    /*Getters and setters*/

    public String getPseudo()
    {
        return pseudo;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public int getBlocksBroken()
    {
        return blocksBroken;
    }

    public void setBlocksBroken(int blocksBroken)
    {
        this.blocksBroken = blocksBroken;
    }

    public int getBlocksPlaced()
    {
        return blocksPlaced;
    }

    public void setBlocksPlaced(int blocksPlaced)
    {
        this.blocksPlaced = blocksPlaced;
    }

    public int getStupidDeath()
    {
        return stupidDeaths;
    }

    public void setStupidDeaths(int stupidDeaths)
    {
        this.stupidDeaths = stupidDeaths;
    }

    public int getNormalDeaths()
    {
        return normalDeaths;
    }

    public void setNormalDeath(int normalDeaths)
    {
        this.normalDeaths = normalDeaths;
    }

    public int getKills()
    {
        return kills;
    }

    public void setKills(int kills)
    {
        this.kills = kills;
    }

    public int getTimePlayed()
    {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed)
    {
        this.timePlayed = timePlayed;
    }

    public int getVerbosity()
    {
        return verbosity;
    }

    public void setVerbosity(int verbosity)
    {
        this.verbosity = verbosity;
    }
}
