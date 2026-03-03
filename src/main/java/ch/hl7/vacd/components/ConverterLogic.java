/**
 * 
 */
package ch.hl7.vacd.components;

import java.io.File;
import java.net.URI;

/**
 * 
 */
public interface ConverterLogic {

	void convertNuvaToSwissmedic(URI sourceURI, File destinationFile) throws Exception;

	void convertSwissmedicToNuva(URI sourceURI, File destinationFile) throws Exception;

}
