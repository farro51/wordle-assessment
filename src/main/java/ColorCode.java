public enum ColorCode {
    WHITE("\033[0m"),
    YELLOW("\033[30;43;1m"),
    GREEN("\033[30;42;2m");

    private final String code;

    ColorCode(String characterCode) {
        code = characterCode;
    }

    public String getCode() {return code;}
}