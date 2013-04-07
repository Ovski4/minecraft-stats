package studmine.studstats;

/**
 * PlayerStats
 * Store and manage the statistics of a player (this class mapped the stats table)
 * Each object refers to a single player
 * @author Ovski
 */
public class PlayerStats
{
    private String pseudo;
    private int blocksBroken;
    private int blocksPlaced;
    private int stupidDeaths;
    private int normalDeaths;
    private int kills;
    private long timePlayed; //in milliseconds
    private int verbosity;
    private long timeSinceLastSave; //in milliseconds
    private int prestige;

    /**
     * getFormattedTimePlayed method format the TimePlayed in String
     * @return String formattedTime : Contains the formatted (Xh, Xmin, Xsec) played time
     */
    public String getFormattedTimePlayed()
    {
        long seconds = (timePlayed / 1000) % 60;
        long minutes = (timePlayed / (1000*60)) % 60;
        long hours   = timePlayed / (1000*60*60);
        return String.valueOf(hours)+" h, "+String.valueOf(minutes)+" min, "+String.valueOf(seconds)+" sec";
    }

    /**
     * getRatio method calcuate the kill/death ratio
     * @return float ratio
     */
    public float getRatio()
    {
        if ((stupidDeaths+normalDeaths)==0)
        {
            return Float.valueOf(kills);
        }
        else
        {
            return (Float.valueOf(kills)/(Float.valueOf(stupidDeaths)+Float.valueOf(normalDeaths)));
        }
    }

    /**
     * getTotalDeaths method calcuate the total number of deaths (normal and stupid ones)
     * @return int totalDeaths
     */
    public int getTotalDeaths()
    {
        return stupidDeaths+normalDeaths;
    }

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

    public void incrementVerbosity()
    {
        this.verbosity++;
    }

    /*Getters and setters*/

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

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

    public int getStupidDeaths()
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

    public void setNormalDeaths(int normalDeaths)
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

    public long getTimePlayed()
    {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed)
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

    public long getTimeSinceLastSave()
    {
        return timeSinceLastSave;
    }

    public void setTimeSinceLastSave(long timeSinceLastSave)
    {
        this.timeSinceLastSave = timeSinceLastSave;
    }
}
