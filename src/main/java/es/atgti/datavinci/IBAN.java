package es.atgti.datavinci;

import lombok.Value;

import java.util.Optional;

@Value
public class IBAN {

    IBANCountryCode countryCode;
    String checkDigits;
    String bban;
    boolean valid;
    IBANValidationInfo validationInfo;

    public Optional<IBANCountryCode> getCountryCode(){
        return Optional.ofNullable(countryCode);
    }

    public Optional<String> getCheckDigits(){
        return Optional.ofNullable(checkDigits);
    }

    public Optional<String> getBban(){
        return Optional.ofNullable(bban);
    }

    public static IBAN of(String iban) {
        return IBANUtils.of(iban);
    }

    public Optional<String> format() {
        return IBANUtils.format(this);
    }

}
