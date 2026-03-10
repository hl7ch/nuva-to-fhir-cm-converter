package ch.hl7.vacd;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.hl7.vacd.components.ConverterLogic;
import ch.hl7.vacd.configuration.MigrationConfiguration;

@SpringBootApplication
public class NuvaToFhirCmConverterApplication implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(NuvaToFhirCmConverterApplication.class);

	@Autowired
	private ConverterLogic converterLogic;

	@Autowired
	private MigrationConfiguration migrationConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(NuvaToFhirCmConverterApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (args.containsOption("destination") || args.containsOption("d")) {
			File destinationFile = new File(args.containsOption("destination") ? //
					args.getOptionValues("destination").get(0) : //
					args.getOptionValues("d").get(0));

			migrationConfiguration.getItems().forEach(item -> {
				System.out.println("Item: " + item);

				try {
					converterLogic.convertNuvaToCode(destinationFile, item);
				} catch (Exception e) {
					logger.error("Error converting Nuva to Code for item: " + item.getName(), e);
				}
				try {
					converterLogic.convertCodeToNuva(destinationFile, item);
				} catch (Exception e) {
					logger.error("Error converting Code to Nuva for item: " + item.getName(), e);
				}

			});

		}
	}

}
