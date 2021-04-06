package Java;

public enum Category {
    AKTUALITET("Aktualitet"),
    DOKUMENTAR("Dokumentar"),
    MAGASIN("Magasin"),
    KULTUR_OG_NATUR("Kultur og Natur"),
    DRAMA("Drama"),
    TV_SERIE("TV-Serie"),
    UNDERHOLDNING("Underholdning"),
    MUSIK("Musik"),
    BØRN("Børn"),
    REGIONALPROGRAM("Regionalprogram"),
    SPORT("Sport"),
    NYHEDER("Nyheder"),
    FILM("Film");

    private String categoryString;

    Category(String categoryString){
        this.categoryString = categoryString;
    }

    @Override
    public String toString() {
        return categoryString;
    }
}
