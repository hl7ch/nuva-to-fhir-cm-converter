/**
 * 
 */
package ch.hl7.vacd.components.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import ch.hl7.vacd.components.SwissmedicToNuvaParser;
import ch.hl7.vacd.domain.SwissmedicToNuva;

/**
 * 
 */
@Component
public class SwissmedicToNuvaParserImpl implements SwissmedicToNuvaParser {

	@Override
	public List<SwissmedicToNuva> parseCsv(InputStreamResource resource) throws IOException {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			CsvToBean<SwissmedicToNuva> csvToBean = new CsvToBeanBuilder<SwissmedicToNuva>(reader)//
					.withType(SwissmedicToNuva.class)//
					.withIgnoreLeadingWhiteSpace(true)//
					.withSkipLines(1)//
					.build();
			return csvToBean.parse();
		}
	}

}