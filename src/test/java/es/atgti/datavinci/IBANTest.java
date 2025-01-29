package es.atgti.datavinci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class IBANTest {

    @Test
    @DisplayName("Debería poder crear un objeto IBAN desde un texto") //Happy path
    void okIBANsFromTextTest(){
        IBAN miIBAN = IBAN.of("ES9121000418450200051332");
        assertNotNull(miIBAN);
        assertEquals(IBANCountryCode.ES, miIBAN.getCountryCode().orElseThrow());
        assertEquals("91", miIBAN.getCheckDigits().orElseThrow());
        assertEquals("21000418450200051332", miIBAN.getBban().orElseThrow());
        assertEquals("ES9121000418450200051332", miIBAN.format().orElseThrow());
        assertTrue(miIBAN.isValid());
        assertEquals(IBANValidationInfo.OK, miIBAN.getValidationInfo());
    }

    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto IBAN desde un texto que contenga un patrón raro") //Happy path
    @CsvFileSource(resources = "/ibanes-ok.csv")
    void okIBANsFromWeirdTextTest(String iban, String countryCode, String checkDigits, String bban){
        IBAN miIBAN = IBAN.of(iban);
        assertNotNull(miIBAN);
        assertEquals(IBANCountryCode.of(countryCode).orElseThrow(), miIBAN.getCountryCode().orElseThrow());
        assertEquals(checkDigits, miIBAN.getCheckDigits().orElseThrow());
        assertEquals(bban, miIBAN.getBban().orElseThrow());
        assertTrue(miIBAN.isValid());
        assertEquals(IBANValidationInfo.OK, miIBAN.getValidationInfo());
    }
    @ParameterizedTest
    @DisplayName("Debería poder crear un objeto IBAN desde un texto que contenga un patrón raro") //Happy path
    @CsvFileSource(resources = "/ibanes-nok.csv")
    void okIBANsFromWeirdTextTest(String iban){
        IBAN miIBAN = IBAN.of(iban);
        assertNotNull(miIBAN);
        assertFalse(miIBAN.isValid());
        assertEquals(IBANValidationInfo.INVALID_FORMAT, miIBAN.getValidationInfo());
    }

    @Test
    void generateRandomValidIBAN() {
        IBAN iban = IBAN.of("ES", null, (""+Math.random() +Math.random()+Math.random()+Math.random()  ).replace(".","").substring(0, 20));
        assertNotNull(iban);
        assertTrue(iban.isValid());
        System.out.println(iban.format().orElseThrow());
    }
}
