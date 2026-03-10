/**
 * 
 */
package ch.hl7.vacd.components;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;

import ch.hl7.vacd.domain.CodeToNuva;

/**
 * 
 */
public interface SwissmedicToNuvaParser {

	public List<CodeToNuva> parseCsv(InputStreamResource resource) throws IOException;

}
