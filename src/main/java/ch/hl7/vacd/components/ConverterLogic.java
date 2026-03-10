/**
 * 
 */
package ch.hl7.vacd.components;

import java.io.File;

/**
 * 
 */
public interface ConverterLogic {

	void convertNuvaToSwissmedic(File destinationFile) throws Exception;

	void convertSwissmedicToNuva(File destinationFile) throws Exception;

	void convertNuvaToSwisslegacy(File destinationFile) throws Exception;

	void convertSwisslegacyToNuva(File destinationFile) throws Exception;
}
