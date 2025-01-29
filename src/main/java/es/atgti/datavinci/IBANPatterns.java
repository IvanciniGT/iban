package es.atgti.datavinci;

import java.util.regex.Pattern;

public class IBANPatterns {

        public  static final Pattern IBAN_PATTERN = Pattern.compile("([A-Z]{2})(\\d{2})(([ _.-]*[A-Z0-9]){4})(([ _.-]*\\d){7,26})");
        public static final Pattern EMPTY_DIGITS_PATTERN = Pattern.compile("[ _.-]");
}