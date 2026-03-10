/**
 * 
 */
package ch.hl7.vacd.components;

import java.io.File;

import ch.hl7.vacd.domain.MigrationItems;

/**
 * 
 */
public interface ConverterLogic {
	void convertNuvaToCode(File destinationDir, MigrationItems item) throws Exception;

	void convertCodeToNuva(File destinationDir, MigrationItems item) throws Exception;

	String lookupDisplayForCode(String system, String code);
}
