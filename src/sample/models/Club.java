package sample.models;

public class Club {
    private final String clubName;
    private String logoId, shirtLogo;

    public Club(String clubName, String logoId, String shirtLogo){
        this.clubName = clubName;
        this.logoId = logoId;
        this.shirtLogo = shirtLogo;
    }

    @Override
    public String toString() {
        return getClubName();
    }

    public String getClubName() {
        return clubName;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getShirtLogo() {
        return shirtLogo;
    }

    public void setShirtLogo(String shirtLogo) {
        this.shirtLogo = shirtLogo;
    }
}
