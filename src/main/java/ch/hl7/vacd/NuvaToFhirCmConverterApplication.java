package ch.hl7.vacd;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.hl7.vacd.components.ConverterLogic;

@SpringBootApplication
public class NuvaToFhirCmConverterApplication implements ApplicationRunner {

	@Autowired
	private ConverterLogic converterLogic;

	public static void main(String[] args) {
		SpringApplication.run(NuvaToFhirCmConverterApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (args.containsOption("destination") || args.containsOption("d")) {
			File destinationFile = new File(args.containsOption("destination") ? //
					args.getOptionValues("destination").get(0) : //
					args.getOptionValues("d").get(0));

			converterLogic.convertNuvaToSwissmedic(destinationFile);
			converterLogic.convertSwissmedicToNuva(destinationFile);
			converterLogic.convertNuvaToSwisslegacy(destinationFile);
			converterLogic.convertSwisslegacyToNuva(destinationFile);

		}

//		if ((args.containsOption("source") || args.containsOption("s"))
//				&& (args.containsOption("destination") || args.containsOption("d"))) {
//			logger.info("Running with arguments: {}", args.getOptionNames());
//			URI sourceURI = new URI(args.containsOption("source") ? args.getOptionValues("source").get(0)
//					: args.getOptionValues("s").get(0));
//			File destinationFile = new File(
//					args.containsOption("destination") ? args.getOptionValues("destination").get(0)
//							: args.getOptionValues("d").get(0));
//			if (args.containsOption("fromnuva")) {
//
//				converterLogic.convertNuvaToSwissmedic(sourceURI, destinationFile);
//
//			} else if (args.containsOption("fromswissmedic")) {
//
//				converterLogic.convertSwissmedicToNuva(sourceURI, destinationFile);
//
//			}
//		} else {
//			System.out.println("No arguments provided.");
//		}
		System.exit(0);
	}

}
