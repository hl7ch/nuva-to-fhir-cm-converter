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
import ch.hl7.vacd.domain.NuvaToSwissmedic;

/**
 * 
 */
@Component
public class NuvaToSwissmedicParserImpl implements NuvaToSwissmedicParser {

	@Override
	public List<NuvaToSwissmedic> parseCsv(InputStreamResource resource) throws IOException {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			CsvToBean<NuvaToSwissmedic> csvToBean = new CsvToBeanBuilder<NuvaToSwissmedic>(reader)//
					.withType(NuvaToSwissmedic.class)//
					.withIgnoreLeadingWhiteSpace(true)//
					.withSkipLines(1)//
					.build();
			return csvToBean.parse();
		}
	}

}
