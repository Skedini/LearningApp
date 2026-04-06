public class Word {
    private String pronoun;
    private String word;
    private String translation;
    public Word(String pronoun, String word, String translation) {
        this.pronoun = pronoun;
        this.word = word;
        this.translation = translation;
    }
    public String getPronoun() {
        return pronoun;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
