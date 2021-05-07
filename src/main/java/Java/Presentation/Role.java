package Java.Presentation;

public enum Role {
    SKUESPILLER("Skuespiller"),
    BILLEDKUNSTNERE("Billedkunstnere"),
    BILLED_OG_LYDREDIGERING("Billed- og lydredigering"),
    CASTING("Casting"),
    COLOURGRADING("Colourgrading"),
    DIRIGENTER("Dirigenter"),
    DRONE("Drone"),
    DUKKEFØRER("Dukkefører"),
    DUKKESKABER("Dukkeskaber"),
    FORTÆLLER("Fortæller"),
    FOTOGRAFER("Fotografer"),
    FORLÆG("Forlæg"),
    GRAFISKE_DESIGNERE("Grafiske designere"),
    INDTALERE("Indtalere"),
    KAPELMESTER("Kapelmester"),
    KLIPPER("Klipper"),
    KONCEPT_PROGRAMKONCEPT("Koncept/programkoncept"),
    KONSULENT("Konsulent"),
    KOR("Kor"),
    KOREOGRAFI("Koreografi"),
    LYD_ELLER_TONEMESTER("Lyd eller tonemester"),
    LYDREDIGERING("Lydredigering"),
    LYS("Lys"),
    MEDVIRKENDE("Medvirkende"),
    MUSIKERE("Musikere"),
    MUSIKALSK_ARRANGEMENT("Musikalsk arrangement"),
    ORKESTER_BAND("Orkester/band"),
    OVERSÆTTERE("Oversættere"),
    PRODUCENT("Producent"),
    PRODUCER("Producer"),
    PRODUKTIONSKOORDINATOR_EL_PRODUKTIONSLEDER("Produktionskoordinator el produktionsleder"),
    PROGRAMANSVARLIGE("Programansvarlige"),
    REDAKTION_TILRETTELÆGGELSE("Redaktion/tilrettelæggelse"),
    REDAKTØREN("Redaktøren"),
    REKVISITØR("Rekvisitør"),
    SCENOGRAFER("Scenografer"),
    SCRIPTER_PRODUCERASSISTENT("Scripter/producerassistent"),
    SPECIAL_EFFECTS("Special effects"),
    SPONSORER("Sponsorer"),
    TEGNEFILM_ELLER_ANIMATION("Tegnefilm eller animation"),
    TEKSTERE("Tekstere"),
    TEKST_OG_MUSIK("Tekst og musik"),
    UHONORERET_OG_EKSTRAORDINÆR_INDSATS("Uhonoreret og ekstraordinær indsats");

    private String roleString;

    Role(String roleString){
        this.roleString = roleString;
    }

    public Role getRoleFromString(String val){
        Role[] roles = Role.values();
        for (int i = 0; i < Role.values().length; i++) {
            if (val.equals(roles[i].toString())){
                return roles[i];
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return roleString;
    }
}
