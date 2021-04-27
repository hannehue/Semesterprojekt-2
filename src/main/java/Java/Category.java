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


    public Category getCategoryFromString(String val){
        Category[] categories = Category.values();
        for (int i = 0; i < Category.values().length; i++) {
            if (val.equals(categories[i].toString())){
                return categories[i];
            }
        }
        return null;
    }

    public Category[] getCategoriesFromString(String categoryString){
        String[] splitCategories = categoryString.split(";");
        Category[] categories = new Category[splitCategories.length];
        for (int i = 0; i < splitCategories.length; i++){
            categories[i] = getCategoryFromString(splitCategories[i]);
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
