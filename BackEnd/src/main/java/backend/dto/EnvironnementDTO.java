package backend.dto;

public class EnvironnementDTO {
    private String airStatus;
    private String airColor;
    private String pollenStatus;
    private String pollenColor;

    public String getAirStatus() { return airStatus; }
    public void setAirStatus(String airStatus) { this.airStatus = airStatus; }
    public String getAirColor() { return airColor; }
    public void setAirColor(String airColor) { this.airColor = airColor; }
    public String getPollenStatus() { return pollenStatus; }
    public void setPollenStatus(String pollenStatus) { this.pollenStatus = pollenStatus; }
    public String getPollenColor() { return pollenColor; }
    public void setPollenColor(String pollenColor) { this.pollenColor = pollenColor; }
}