public class StatisticalAnalyzer {

    private final Cipher cipher;

    public StatisticalAnalyzer(Cipher cipher) {
        this.cipher = cipher;
    }

    public AnalysisResult analyze(String зашифрованныйТекст) {
        int лучшийКлюч = 0;
        int лучшийБалл = Integer.MIN_VALUE;
        String лучшийТекст = "";

        for (int ключ = 0; ключ < cipher.getAlphabetSize(); ключ++) {
            String вариант = cipher.decrypt(зашифрованныйТекст, ключ);
            int балл = calculateTextScore(вариант);

            if (балл > лучшийБалл) {
                лучшийБалл = балл;
                лучшийКлюч = ключ;
                лучшийТекст = вариант;
            }
        }

        return new AnalysisResult(лучшийКлюч, лучшийТекст, лучшийБалл);
    }

    private int calculateTextScore(String текст) {
        int балл = 0;
        String нижнийТекст = текст.toLowerCase();

        for (char символ : нижнийТекст.toCharArray()) {
            if (" оеаинтсрвлкмдпуяызьгбчйхжюшцщэфъ".indexOf(символ) >= 0) {
                балл += 2;
            }

            if (символ == ' ') {
                балл += 5;
            }

            if (символ == '.' || символ == ',' || символ == '!' || символ == '?') {
                балл += 1;
            }
        }

        String[] частыеСлова = {
                " и ", " в ", " на ", " не ", " что ", " как ", " это ",
                " по ", " с ", " для ", " из ", " я ", " он ", " она ",
                " мы ", " вы ", " они ", " текст ", " файл "
        };

        for (String слово : частыеСлова) {
            if (нижнийТекст.contains(слово)) {
                балл += 20;
            }
        }

        for (char символ : нижнийТекст.toCharArray()) {
            if (!Character.isLetter(символ)
                    && символ != ' '
                    && символ != '.'
                    && символ != ','
                    && символ != '!'
                    && символ != '?'
                    && символ != ':'
                    && символ != ';'
                    && символ != '-'
                    && символ != '\n'
                    && символ != '\r') {
                балл -= 5;
            }
        }

        return балл;
    }

    public static class AnalysisResult {
        private final int ключ;
        private final String расшифрованныйТекст;
        private final int балл;

        public AnalysisResult(int ключ, String расшифрованныйТекст, int балл) {
            this.ключ = ключ;
            this.расшифрованныйТекст = расшифрованныйТекст;
            this.балл = балл;
        }

        public int getКлюч() {
            return ключ;
        }

        public String getРасшифрованныйТекст() {
            return расшифрованныйТекст;
        }

        public int getБалл() {
            return балл;
        }
    }
}