package jackah2.hellolangfeed;

/**
 * Created by xmrpo on 10/6/2017.
 */

public enum Language {
    ENGLISH(R.string.english),
    SPANISH(R.string.spanish),
    FRENCH(R.string.french),
    MANDARIN(R.string.mandarin);

    private int langID;

    Language(int langID){
        this.langID = langID;
    }

    @Override
    public String toString() {
        return Feed.getContext().getResources().getString(langID);
    }

    public static Language match(String str){
        for(Language lang : Language.values())
            if(lang.toString().equalsIgnoreCase(str))
                return lang;
        return null;
    }
}
