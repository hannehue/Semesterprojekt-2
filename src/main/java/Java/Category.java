package Java;

import java.util.ArrayList;

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


    public static Category[] getCategoriesFromString(String categoryString){
        String[] categoryStrings = categoryString.split(";");
        Category[] categories = new Category[categoryStrings.length];

        for(int i = 0; i < categories.length; i++){
            categories[i] = Category.valueOf(categoryStrings[i]);
        }
        return categories;
    }

    Category(String categoryString){
        this.categoryString = categoryString;
    }

    @Override
    public String toString() {
        return categoryString;
    }
}
