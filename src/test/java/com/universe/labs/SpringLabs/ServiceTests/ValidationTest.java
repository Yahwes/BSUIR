package com.universe.labs.SpringLabs.ServiceTests;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.universe.labs.exceptions.YearException;
import com.universe.labs.service.YearValidation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@RunWith(SpringRunner.class)
@SpringBootTest
class ValidationTest {

    private YearValidation yearValidation;

    @BeforeEach
    void setUp() {
        yearValidation = new YearValidation();
    }

    @Test
    void testCheckParam_ValidNumber_ReturnsParsedNumber() {
        String number = "2023";
        int expectedResult = 2023;

        int result = yearValidation.checkParam(number);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testCheckParam_TooBigNumber_ThrowsYearException() {
        String number = "123456789012";

        Assertions.assertThrows(YearException.class, () -> {
            yearValidation.checkParam(number);
        });
    }

    @Test
    void testCheckParam_NumberWithCharacter_ThrowsYearException() {
        String number = "2023a";

        Assertions.assertThrows(YearException.class, () -> {
            yearValidation.checkParam(number);
        });
    }

    @Test
    void testCheckParam_NegativeNumber_ThrowsYearException() {
        String number = "-2023";

        Assertions.assertThrows(YearException.class, () -> {
            yearValidation.checkParam(number);
        });
    }

    @Test
    void testCheckLength_LongNumber_ReturnsTrue() {
        String longNumber = "123456789012";

        boolean result = yearValidation.checkLength(longNumber);

        Assertions.assertTrue(result);
    }

    @Test
    void testCheckLength_ShortNumber_ReturnsFalse() {
        String shortNumber = "2023";

        boolean result = yearValidation.checkLength(shortNumber);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsAlpha_AlphaNumericString_ReturnsTrue() {
        String alphaNumericString = "2023a";

        boolean result = yearValidation.isAlpha(alphaNumericString);

        Assertions.assertTrue(result);
    }

    @Test
    void testIsAlpha_NumericString_ReturnsFalse() {
        String numericString = "2023";

        boolean result = yearValidation.isAlpha(numericString);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsNotValid_NegativeNumber_ReturnsTrue() {
        int negativeNumber = -2023;

        boolean result = yearValidation.isNotValid(negativeNumber);

        Assertions.assertTrue(result);
    }

    @Test
    void testIsNotValid_PositiveNumber_ReturnsFalse() {
        int positiveNumber = 2023;

        boolean result = yearValidation.isNotValid(positiveNumber);

        Assertions.assertFalse(result);
    }
}
