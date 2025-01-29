package es.atgti.datavinci;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum IBANCountryCode {

    AD("Andorra", "AD", 24),
    AE("United Arab Emirates", "AE", 23),
    AL("Albania", "AL", 28),
    AT("Austria", "AT", 20),
    AZ("Azerbaijan", "AZ", 28),
    BA("Bosnia and Herzegovina", "BA", 20),
    BE("Belgium", "BE", 16),
    BG("Bulgaria", "BG", 22),
    BH("Bahrain", "BH", 22),
    BR("Brazil", "BR", 29),
    BY("Belarus", "BY", 28),
    CH("Switzerland", "CH", 21),
    CY("Cyprus", "CY", 28),
    CZ("Czech Republic", "CZ", 24),
    DE("Germany", "DE", 22),
    DK("Denmark", "DK", 18),
    DO("Dominican Republic", "DO", 28),
    EE("Estonia", "EE", 20),
    ES("Spain", "ES", 24),
    FI("Finland", "FI", 18),
    FR("France", "FR", 27),
    GB("United Kingdom", "GB", 22),
    GR("Greece", "GR", 27),
    HR("Croatia", "HR", 21),
    HU("Hungary", "HU", 28),
    IE("Ireland", "IE", 22),
    IL("Israel", "IL", 23),
    IS("Iceland", "IS", 26),
    IT("Italy", "IT", 27),
    LT("Lithuania", "LT", 20),
    LU("Luxembourg", "LU", 20),
    LV("Latvia", "LV", 21),
    MC("Monaco", "MC", 27),
    MD("Moldova", "MD", 24),
    ME("Montenegro", "ME", 22),
    MK("North Macedonia", "MK", 19),
    MT("Malta", "MT", 31),
    NL("Netherlands", "NL", 18),
    NO("Norway", "NO", 15),
    PL("Poland", "PL", 28),
    PT("Portugal", "PT", 25),
    RO("Romania", "RO", 24),
    RS("Serbia", "RS", 22),
    RU("Russia", "RU", 33),
    SE("Sweden", "SE", 24),
    SI("Slovenia", "SI", 19),
    SK("Slovakia", "SK", 24),
    SM("San Marino", "SM", 27),
    TR("Turkey", "TR", 26);
    private final String countryName;
    private final String countryCode;
    private final int length;

    public static Optional<IBANCountryCode> of(String countryCodeAsString) {
        for (IBANCountryCode ibanCountryCode : IBANCountryCode.values()) {
            if (ibanCountryCode.getCountryCode().equals(countryCodeAsString)) {
                return Optional.of(ibanCountryCode);
            }
        }
        return Optional.empty();
    }
}
