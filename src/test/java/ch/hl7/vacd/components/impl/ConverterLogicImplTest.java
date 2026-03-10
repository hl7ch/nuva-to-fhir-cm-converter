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
	void testLookupDisplayForCode1() {
		String ref = converterLogic.lookupDisplayForCode("http://snomed.info/sct", "428601009");
		assertNotNull(ref);
		assertEquals("Paratyphoid vaccine", ref);
	}
	
	/**
	 * Test method for
	 * {@link ch.hl7.vacd.components.impl.ConverterLogicImpl#lookupDisplayForCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testLookupDisplayForCode2() {
		String ref = converterLogic.lookupDisplayForCode("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-myvaccines-cs", "39");
		assertNotNull(ref);
		assertEquals("Infanrix Penta", ref);
	}
	

	/**
	 * Test method for
	 * {@link ch.hl7.vacd.components.impl.ConverterLogicImpl#lookupDisplayForCode(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testLookupDisplayForCode3() {
		String ref = converterLogic.lookupDisplayForCode("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-swissmedic-cs", "683");
		assertNotNull(ref);
		assertEquals("FSME-Immun 0.25 ml Junior", ref);
	}

}
