/**
 * 
 */
package ch.hl7.vacd.components;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;

import ch.hl7.vacd.domain.SwissmedicToNuva;

/**
 * 
 */
public interface SwissmedicToNuvaParser {

	public List<SwissmedicToNuva> parseCsv(InputStreamResource resource) throws IOException;

}
