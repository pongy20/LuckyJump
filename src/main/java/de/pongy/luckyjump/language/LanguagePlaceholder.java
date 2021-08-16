package de.pongy.luckyjump.language;

public class LanguagePlaceholder {

    private final String placeholdername;
    private final String replacement;

    public LanguagePlaceholder(String placeholdername, String replacement) {
        this.placeholdername = placeholdername;
        this.replacement = replacement;
    }

    public boolean doesMatches(LanguagePlaceholder placeholder) {
        return placeholder.getPlaceholdername() == this.getPlaceholdername();
    }

    public String getPlaceholdername() {
        return placeholdername;
    }

    public String getReplacement() {
        return replacement;
    }
}
