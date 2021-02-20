package gabriel.medpel.superheroescardbattlegame.models;

public class HeroCard {
    private String heroName;
    private String urlImage;
    private String intelligence;
    private String strength;
    private String speed;
    private String durability;
    private String power;
    private String combat;
    private String gender;
    private String race;
    private String height;
    private String weight;
    private String eyeColor;
    private String hairColor;
    private String biography;
    private String work;
    private String connections;
    private String smallImageUrl;

    public HeroCard() {
    }

    public HeroCard(String heroName, String urlImage, String intelligence, String strength, String speed, String durability, String power, String combat, String gender, String race, String height, String weight, String eyeColor, String hairColor, String biography, String work, String connections, String smallImageUrl) {
        this.heroName = heroName;
        this.urlImage = urlImage;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.biography = biography;
        this.work = work;
        this.connections = connections;
        this.smallImageUrl = smallImageUrl;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public String getStrength() {
        return strength;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDurability() {
        return durability;
    }

    public String getPower() {
        return power;
    }

    public String getCombat() {
        return combat;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getBiography() {
        return biography;
    }

    public String getWork() {
        return work;
    }

    public String getConnections() {
        return connections;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }
}

