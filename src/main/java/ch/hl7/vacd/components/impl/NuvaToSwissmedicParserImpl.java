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

import ch.hl7.vacd.components.NuvaToSwissmedicParser;
import ch.hl7.vacd.domain.NuvaToCode;

/**
 * 
 */
@Component
public class NuvaToSwissmedicParserImpl implements NuvaToSwissmedicParser {

	@Override
	public List<NuvaToCode> parseCsv(InputStreamResource resource) throws IOException {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			CsvToBean<NuvaToCode> csvToBean = new CsvToBeanBuilder<NuvaToCode>(reader)//
					.withType(NuvaToCode.class)//
					.withIgnoreLeadingWhiteSpace(true)//
					.withSkipLines(1)//
					.build();
			return csvToBean.parse();
		}
	}

}
