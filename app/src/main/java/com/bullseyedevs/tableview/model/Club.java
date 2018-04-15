package com.bullseyedevs.tableview.model;

public class Club
{
    public String name;
    public String logoUrl;
    public String location;
    public String stadiumName;
    public String leagueName;
    public String coachName;
    public String starPlayerName;

    public Club(String name, String logoUrl, String location, String stadiumName, String leagueName, String coachName, String starPlayerName)
    {
        this.name = name;
        this.logoUrl = logoUrl;
        this.location = location;
        this.stadiumName = stadiumName;
        this.leagueName = leagueName;
        this.coachName = coachName;
        this.starPlayerName = starPlayerName;
    }
}
