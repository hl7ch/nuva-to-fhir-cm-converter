/**
 * 
 */
package ch.hl7.vacd.components.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import ch.hl7.vacd.components.ConverterLogic;

/**
 * 
 */
@SpringBootTest
@AutoConfiguration
class ConverterLogicImplTest {

	@Autowired
	private ConverterLogic converterLogic;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ch.hl7.vacd.components.impl.ConverterLogicImpl#convertNuvaToCode(java.io.File, ch.hl7.vacd.domain.MigrationItems)}.
	 */
	@Test
	@Disabled("Not yet implemented")
	void testConvertNuvaToCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ch.hl7.vacd.components.impl.ConverterLogicImpl#convertCodeToNuva(java.io.File, ch.hl7.vacd.domain.MigrationItems)}.
	 */
	@Test
	@Disabled("Not yet implemented")
	void testConvertCodeToNuva() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ch.hl7.vacd.components.impl.ConverterLogicImpl#lookupDisplayForCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testLookupDisplayForCode() {
		String ref = converterLogic.lookupDisplayForCode("http://snomed.info/sct", "737269009");
		assertNotNull(ref);
		assertEquals("COVID-19 (disorder)", ref);
	}

}
