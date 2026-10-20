import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordCheckerTest {
    PasswordChecker checker;

    @BeforeEach
    void setUp() {
        checker = new PasswordChecker();
    }

    @Test
    void passwordCorrecto() {
        int resultado = checker.check("abcde123", "abcde123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void passwordEmpty(){
        int resultado = checker.check("", "");
        assertEquals(PasswordChecker.ERR_BLANK, resultado);
    }
    @Test
    void passwordBlank(){
        int resultado = checker.check("  ", "  ");
        assertEquals(PasswordChecker.ERR_BLANK, resultado);
    }

    @Test
    void passwordLongMax(){
        checker.setMaxLength(12);
        int resultado = checker.check("Hola12345678", "Hola12345678");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void passwordTooLong(){
        checker.setMinLength(6);
        checker.setMaxLength(12);
        int resultado = checker.check("Hola123456789", "Hola123456789");
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }

    @Test
    void passwordLongOk(){
        checker.setMinLength(6);
        checker.setMaxLength(12);
        int resultado = checker.check("Hola1234567", "Hola1234567");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void passwordShortMin(){
        checker.setMinLength(6);
        checker.setMaxLength(12);
        int resultado = checker.check("Hola12", "Hola12");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void passwordTooShort(){
        checker.setMinLength(6);
        checker.setMaxLength(12);
        int resultado = checker.check("Hola1", "Hola1");
        assertEquals(PasswordChecker.ERR_LENGTH, resultado);
    }

    @Test
    void passwordShortOk(){
        checker.setMinLength(6);
        checker.setMaxLength(12);
        int resultado = checker.check("Hola123", "Hola123");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void setLengthMax(){
        checker.setMaxLength(12);
        assertEquals(12, checker.getMaxLength());
    }

    @Test
    void minLengthNegativa(){
        assertThrows(IllegalArgumentException.class, () -> checker.setMinLength(-1));
    }

    @Test
    void MaxLengthIsLessThanMinLength(){
        checker.setMinLength(6);
        assertThrows(IllegalArgumentException.class, () -> checker.setMaxLength(4));
    }
    @Test
    void minLengthZeroDontThrowException(){
        checker.setMinLength(0);
        assertEquals(0, checker.getMinLength());
    }

    @Test
    void minLengthOneDotThrowException(){
        checker.setMinLength(1);
        assertEquals(1, checker.getMinLength());
    }

    @Test
    void maxLengthEqualToMinDontThrowException(){
        checker.setMinLength(6);
        checker.setMaxLength(6);
        assertEquals(6, checker.getMaxLength());
    }

    @Test
    void maxLengthAboveMinDontThrowException(){
        checker.setMinLength(6);
        checker.setMaxLength(7);
        assertEquals(7, checker.getMaxLength());
    }

    @Test
    void doesntHaveDigitsTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckDigits(true);
        int resultado = checker.check("holaaaaa", "holaaaaa");
        assertEquals(PasswordChecker.ERR_DIGITS, resultado);
    }

    @Test
    void doesntHaveDigitsfalse(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckDigits(false);
        int resultado = checker.check("holaaaaa", "holaaaaa");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void doesHaveDigitsTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckDigits(true);
        int resultado = checker.check("holaaaaa1234", "holaaaaa1234");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void doesntHaveUppersTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckUppers(true);
        int resultado = checker.check("holaaaaa1234", "holaaaaa1234");
        assertEquals(PasswordChecker.ERR_UPPERS, resultado);
    }
    @Test
    void doesntHaveUppersFalse(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckUppers(false);
        int resultado = checker.check("holaaaaa1234", "holaaaaa1234");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }
    @Test
    void doesHaveUppersTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckUppers(true);
        int resultado = checker.check("Holaaaaa1234", "Holaaaaa1234");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void doesntHaveLowersTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckLowers(true);
        int resultado = checker.check("HOOLA1234", "HOOLA1234");
        assertEquals(PasswordChecker.ERR_LOWERS, resultado);
    }
    @Test
    void doesntHaveLowersFalse(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckLowers(false);
        int resultado = checker.check("HOOLA1234", "HOOLA1234");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }
    @Test
    void doesHaveLowersTrue(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        checker.setCheckLowers(true);
        int resultado = checker.check("Hoola1234", "Hoola1234");
        assertEquals(PasswordChecker.PASSWORD_OK, resultado);
    }

    @Test
    void passwordDoesntMatch(){
        checker.setMinLength(1);
        checker.setMaxLength(20);
        int resultado = checker.check("Hola123", "HolA123");
        assertEquals(PasswordChecker.ERR_NO_MATCH, resultado);
    }

    @Test
    void checkDigitsDefaultIsFalse(){
        assertFalse(checker.getCheckDigits());
    }
    @Test
    void checkDigitsSetTrueReturnsTrue(){
        checker.setCheckDigits(true);
        assertTrue(checker.getCheckDigits());
    }
    @Test
    void checkDigitsSetFalseReturnsFalse(){
        checker.setCheckDigits(true);
        checker.setCheckDigits(false);
        assertFalse(checker.getCheckDigits());
    }

    @Test
    void checkUppersDefaultIsFalse(){
        assertFalse(checker.getCheckUppers());
    }

    @Test
    void checkUppersSetTrueReturnsTrue(){
        checker.setCheckUppers(true);
        assertTrue(checker.getCheckUppers());
    }

    @Test
    void checkLowersDefaultIsFalse(){
        assertFalse(checker.getCheckLowers());
    }

    @Test
    void checkLowersSetTrueReturnsTrue(){
        checker.setCheckLowers(true);
        assertTrue(checker.getCheckLowers());
    }

}
