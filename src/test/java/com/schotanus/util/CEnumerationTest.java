package com.schotanus.util;

import com.schotanus.science.physics.astronomy.Planet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit test class for class {@link CEnumeration}.
 */
 class CEnumerationTest {

     @Test
    void testGetDescriptionOfEnumeration() {
         // Get existing key (Planet)
         assertEquals("Planets in our solar system", CEnumeration.getDescription(Planet.class, null));

         // Get description from enum without a properties file
         assertThrows(MissingResourceException.class,
                 () -> CEnumeration.getDescription(EnumWithoutPropertiesFile.class, null));

         // Get description from enum with properties file, but without the requested key
         assertEquals("EnumWithPropertiesFile", CEnumeration.getDescription(EnumWithPropertiesFile.class, null));
     }

    @Test
    void testGetDescriptionOfEnumeratedConstant() {
         // Test getting an existing property
         assertEquals("Venus", CEnumeration.getDescription(Planet.VENUS, null));

        // Test getting an existing property
        assertThrows(MissingResourceException.class,
                () -> CEnumeration.getDescription(EnumWithPropertiesFile.NoProperty, null));
    }

    @Test
    void testGetDescriptionOfEnumeratedConstantUsingCode() {
        // Test getting an existing property
        assertEquals("One", CEnumeration.getDescription(
                EnumWithPropertiesFile.ONE, String.valueOf(EnumWithPropertiesFile.ONE.getNumber()), null));
    }

    @Test
    void testGetDescriptions() {
         // Test getting code/descriptions of the planets
        final List<CodeDescription<String>> planets = CEnumeration.getCodeDescriptions(Planet.class, new Locale("nl"));
        assertEquals(8, planets.size());
    }
}

/**
 * Enumeration without a properties file
 */
enum EnumWithoutPropertiesFile {
}

enum EnumWithPropertiesFile {
    NoProperty(0), ONE(1);

    final int code;
    EnumWithPropertiesFile(int code) {
        this.code = code;
    }

    int getNumber() {
        return code;
    }
}
