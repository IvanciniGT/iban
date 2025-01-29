package es.atgti.datavinci;
import java.math.BigInteger;
import java.util.Optional;

import static es.atgti.datavinci.IBANPatterns.EMPTY_DIGITS_PATTERN;

public class IBANUtils {


    public static Optional<String> format(IBAN iban) {
        if(iban.isValid())
            return Optional.of(iban.getCountryCode().get().getCountryCode() + iban.getCheckDigits().orElseThrow() + iban.getBban().orElseThrow());
        else
            return Optional.empty();
    }

    public static IBAN of(String countryCode, String checkDigits, String bban) {
        if(countryCode == null){
            return new IBAN(null, null, null, false, IBANValidationInfo.INVALID_COUNTRY_CODE);
        }
        if(bban == null){
            return new IBAN(null, null, null, false, IBANValidationInfo.INVALID_BBAN);
        }
        if(checkDigits == null){
            checkDigits = calculateIbanCheckDigits(countryCode, bban);
        }
        return of(countryCode + checkDigits + bban);
    }
    public static IBAN of(String iban) {
        if(iban == null){
            return new IBAN(null, null, null, false, IBANValidationInfo.INVALID_NULL_IBAN);
        } else if(iban.isEmpty()) {
            return new IBAN(null, null, null, false, IBANValidationInfo.INVALID_EMPTY_IBAN);
        } else{
            iban = iban.toUpperCase(); // Esto es matar moscas a cañonazos, lo refactorizaremos TODO Le damos una vuelta... Pueden venir letras por medio
            boolean matches = IBANPatterns.IBAN_PATTERN.matcher(iban).matches();
            if(!matches){
                return new IBAN(null, null, null, false, IBANValidationInfo.INVALID_FORMAT);
            }else{
                iban = EMPTY_DIGITS_PATTERN.matcher(iban).replaceAll("");
                String countryCodeAsString = iban.substring(0, 2);
                String checkDigits = iban.substring(2, 4);
                String bban = iban.substring(4);
                // Mirar si el codigo del pais es bueno
                Optional<IBANCountryCode> countryCode = IBANCountryCode.of(countryCodeAsString);
                if(!countryCode.isPresent()){
                    return new IBAN(null, checkDigits, bban, false, IBANValidationInfo.INVALID_COUNTRY_CODE);
                } else { // Si el pais es bueno
                    // Mirar si la longiotud es adecuada al pais
                    if(countryCode.get().getLength() != iban.length()){
                        return new IBAN(countryCode.get(), checkDigits, bban, false, IBANValidationInfo.INVALID_LENGTH);
                    }
                    // Mirar si los digitos de control son correctos
                    String calculatedCheckDigits = calculateIbanCheckDigits(countryCodeAsString, bban);
                    if(!checkDigits.equals(calculatedCheckDigits)) {
                        return new IBAN(countryCode.get(), checkDigits, bban, false, IBANValidationInfo.INVALID_CHECK_DIGITS_ALGORITHM);
                    }else {
                        return new IBAN(countryCode.get(), checkDigits, bban, true, IBANValidationInfo.OK);
                    }
                }
            }
        }
    }


    public static String calculateIbanCheckDigits(String countryCode, String bban) {
        // Convertir letras a números (A=10, B=11, ..., Z=35)
        StringBuilder countryDigits = new StringBuilder();
        for (char ch : countryCode.toCharArray()) {
            countryDigits.append(10 + (ch - 'A'));
        }

        // Formar el número para el cálculo, con "00" en los dígitos de control
        String tempIban = bban + countryDigits.toString() + "00";

        // Calcular el módulo 97
        BigInteger ibanNumber = new BigInteger(tempIban);
        int remainder = ibanNumber.mod(BigInteger.valueOf(97)).intValue();
        int checkDigits = (98 - remainder) % 97;

        return String.format("%02d", checkDigits); // Formato con dos dígitos
    }
}
