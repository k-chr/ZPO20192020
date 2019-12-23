package Kamil215691.ZPO.LAB4.Zad2.TestGenerator;

public class Word{

    public String getBase() {
        return base;
    }

    public String[] getTranslations() {
        return translations;
    }

    public Word(String base, String[] translations) {
        this.base = base;
        this.translations = translations;
    }

    private String base;
    private String[] translations;
}