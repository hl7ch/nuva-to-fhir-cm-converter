/**
 * 
 */
package ch.hl7.vacd.components.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ConceptMap.ConceptMapGroupComponent;
import org.hl7.fhir.r4.model.ConceptMap.SourceElementComponent;
import org.hl7.fhir.r4.model.Enumerations.ConceptMapEquivalence;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.UriType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ch.hl7.vacd.components.ConverterLogic;
import ch.hl7.vacd.components.NuvaToSwissmedicParser;
import ch.hl7.vacd.components.SwissmedicToNuvaParser;
import ch.hl7.vacd.domain.NuvaToCode;
import ch.hl7.vacd.domain.CodeToNuva;

/**
 * 
 */
@Component
public class ConverterLogicImpl implements ConverterLogic {

	private Logger logger = LoggerFactory.getLogger(ConverterLogicImpl.class);

	@Autowired
	private NuvaToSwissmedicParser nuvaToCodeParser;
	@Autowired
	private SwissmedicToNuvaParser codeToNuvaParser;

	@Value("${application.swissmedic.tonuva}")
	private URI swissmedic2NuvaSourceUri;

	@Value("${application.swissmedic.fromnuva}")
	private URI nuva2SwissmedicSourceUri;

	@Value("${application.swisslegacy.tonuva}")
	private URI swisslegacy2NuvaSourceUri;

	@Value("${application.swisslegacy.fromnuva}")
	private URI nuva2SwisslegacySourceUri;

	private FhirContext ctx = FhirContext.forR4();

	@Override
	public void convertNuvaToSwissmedic(File destinationDir) throws Exception {
		List<NuvaToCode> nuvaToCodeList = nuvaToCodeParser.parseCsv(
				new org.springframework.core.io.InputStreamResource(nuva2SwissmedicSourceUri.toURL().openStream()));
		logger.info("NuvaToCodes: {}", nuvaToCodeList.size());
//		nuvaToCodeList.forEach(ctn -> logger.info("{}", ctn));

		ConceptMap cm = new ConceptMap();
		cm.setId("ch-vacd-nuva-to-swissmedic-cm");
		cm.setUrl("http://fhir.ch/ig/ch-vacd/ConceptMap/ch-vacd-nuva-to-swissmedic-cm");
		cm.setName("NuvaToSwissmedicConceptMap");
		cm.setTitle("ConceptMap for mapping Nuva vaccine codes to Swissmedic codes");
		cm.setDescription(
				"This ConceptMap maps vaccine codes from the Nuva terminology to the corresponding codes in the Swissmedic CodeSystem. It is generated based on the provided CSV mapping file and the Swissmedic CodeSystem.");
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType("http://smt.esante.gouv.fr/terminologie-nuva?vs"));
		cm.setTarget(new UriType("http://fhir.ch/ig/ch-vacd/ValueSet/ch-vacd-swissmedic-vaccines-vs"));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource("http://smt.esante.gouv.fr/terminologie-nuva");
		group.setTarget("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-swissmedic-cs");

		CodeSystem swissmdedicCs = getSwissmedicCodesystem();

		nuvaToCode(nuvaToCodeList, group, swissmdedicCs, "SWISSMEDIC-");

		writeToFile(cm, new File(destinationDir, "ch-vacd-nuva-to-swissmedic-cm.json"));

	}

	@Override
	public void convertSwissmedicToNuva(File destinationDir) throws Exception {
		List<CodeToNuva> codeToNuvaList = codeToNuvaParser.parseCsv(
				new org.springframework.core.io.InputStreamResource(swissmedic2NuvaSourceUri.toURL().openStream()));
		logger.info("CodeToNuvas: {}", codeToNuvaList.size());
//		codeToNuvaList.forEach(ctn -> logger.info("{}", ctn));

		ConceptMap cm = new ConceptMap();
		cm.setId("ch-vacd-swissmedic-to-nuva-cm");
		cm.setUrl("http://fhir.ch/ig/ch-vacd/ConceptMap/ch-vacd-swissmedic-to-nuva-cm");
		cm.setName("SwissmedicToNuvaConceptMap");
		cm.setTitle("ConceptMap for mapping Swissmedic vaccine codes to Nuva codes");
		cm.setDescription(
				"This ConceptMap maps vaccine codes from the Swissmedic CodeSystem to the corresponding codes in the Nuva terminology. It is generated based on the provided CSV mapping file and the Swissmedic CodeSystem.");
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType("http://fhir.ch/ig/ch-vacd/ValueSet/ch-vacd-swissmedic-vaccines-vs"));
		cm.setTarget(new UriType("http://smt.esante.gouv.fr/terminologie-nuva?vs"));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-swissmedic-cs");
		group.setTarget("http://smt.esante.gouv.fr/terminologie-nuva");

		CodeSystem swissmdedicCs = getSwissmedicCodesystem();
		codeToNuva(codeToNuvaList, group, swissmdedicCs, "SWISSMEDIC-");

		writeToFile(cm, new File(destinationDir, "ch-vacd-swissmedic-to-nuva-cm.json"));
	}

	@Override
	public void convertNuvaToSwisslegacy(File destinationDir) throws Exception {
		List<NuvaToCode> nuvaToCodeList = nuvaToCodeParser.parseCsv(
				new org.springframework.core.io.InputStreamResource(nuva2SwisslegacySourceUri.toURL().openStream()));

		logger.info("NuvaToCodes: {}", nuvaToCodeList.size());

		ConceptMap cm = new ConceptMap();
		cm.setId("ch-vacd-nuva-to-swisslegacy-cm");
		cm.setUrl("http://fhir.ch/ig/ch-vacd/ConceptMap/ch-vacd-nuva-to-swisslegacy-cm");
		cm.setName("NuvaToSwisslegacyConceptMap");
		cm.setTitle("ConceptMap for mapping Nuva vaccine codes to Old Swiss vaccine codes");
		cm.setDescription(
				"This ConceptMap maps vaccine codes from the Nuva terminology to the corresponding codes in the old swiss vaccines CodeSystem. It is generated based on the provided CSV mapping file and the Swissmedic CodeSystem.");
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType("http://smt.esante.gouv.fr/terminologie-nuva?vs"));
		cm.setTarget(new UriType("http://fhir.ch/ig/ch-vacd/ValueSet/ch-vacd-oldswiss-vaccines-vs"));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource("http://smt.esante.gouv.fr/terminologie-nuva");
		group.setTarget("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-myvaccines-cs");

		CodeSystem swisslegacyCs = getSwisslegacyCodesystem();
		nuvaToCode(nuvaToCodeList, group, swisslegacyCs, "SWISSLEGACY-");

		writeToFile(cm, new File(destinationDir, "ch-vacd-nuva-to-swisslegacy-cm.json"));

	}

	@Override
	public void convertSwisslegacyToNuva(File destinationDir) throws Exception {
		List<CodeToNuva> codeToNuvaList = codeToNuvaParser.parseCsv(
				new org.springframework.core.io.InputStreamResource(swisslegacy2NuvaSourceUri.toURL().openStream()));
		logger.info("CodeToNuvas: {}", codeToNuvaList.size());
//		codeToNuvaList.forEach(ctn -> logger.info("{}", ctn));

		ConceptMap cm = new ConceptMap();
		cm.setId("ch-vacd-swisslegacy-to-nuva-cm");
		cm.setUrl("http://fhir.ch/ig/ch-vacd/ConceptMap/ch-vacd-swisslegacy-to-nuva-cm");
		cm.setName("SwisslegacyToNuvaConceptMap");
		cm.setTitle("ConceptMap for mapping Old Swiss vaccine codes to Nuva vaccine codes");
		cm.setDescription(
				"This ConceptMap maps vaccine codes from the old swiss vaccines CodeSystem to the corresponding codes in the Nuva terminology. It is generated based on the provided CSV mapping file and the Swissmedic CodeSystem.");
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType("http://fhir.ch/ig/ch-vacd/ValueSet/ch-vacd-oldswiss-vaccines-vs"));
		cm.setTarget(new UriType("http://smt.esante.gouv.fr/terminologie-nuva?vs"));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource("http://fhir.ch/ig/ch-vacd/CodeSystem/ch-vacd-myvaccines-cs");
		group.setTarget("http://smt.esante.gouv.fr/terminologie-nuva");

		CodeSystem swisslegacyCs = getSwisslegacyCodesystem();
		codeToNuva(codeToNuvaList, group, swisslegacyCs, "SWISSLEGACY-");

		writeToFile(cm, new File(destinationDir, "ch-vacd-swisslegacy-to-nuva-cm.json"));
	}

	private void writeToFile(ConceptMap cm, File file) throws IOException {
		String cmJson = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(cm);
		logger.info("Generated ConceptMap JSON:\n{}", cmJson);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(cmJson.getBytes());
		fos.close();
	}

	private void nuvaToCode(List<NuvaToCode> nuvaToCodeList, ConceptMapGroupComponent group, CodeSystem swissmdedicCs,
			String replacePrefix) {

		SourceElementComponent element = null;
		for (NuvaToCode ntc : nuvaToCodeList) {
			if (!ntc.isAbstract() && !StringUtils.isEmpty(ntc.getCode())) {
				String code = ntc.getCode().replace(replacePrefix, "");
				String display = lookupDisplayForCode(swissmdedicCs, code);
				if (!StringUtils.isEmpty(display)) {
					if (element != null && element.getCode().equals(ntc.getNuvaCode())) {
						element.addTarget()//
								.setCode(code)//
								.setDisplay(display)//
								.setEquivalence(ConceptMapEquivalence.EQUAL);
						logger.debug("Multiple mappings for Nuva code {}. Added additional target to existing element.",
								ntc.getNuvaCode());
					} else {
						element = group.addElement();

						element.setCode(ntc.getNuvaCode())//
								.setDisplay(ntc.getNuvaLabel())//
								.addTarget()//
								.setCode(code)//
								.setDisplay(display)//
								.setEquivalence(ConceptMapEquivalence.EQUAL);
					}
				}
			}
		}

	}

	private void codeToNuva(List<CodeToNuva> codeToNuvaList, ConceptMapGroupComponent group, CodeSystem swissmdedicCs,
			String replacePrefix) {

		codeToNuvaList.forEach(ntc -> {
			String code = ntc.getCode().replace(replacePrefix, "");
			String display = lookupDisplayForCode(swissmdedicCs, code);
			if (!StringUtils.isEmpty(display)) {
				group.addElement()//
						.setCode(code)//
						.setDisplay(display)//
						.addTarget()//
						.setCode(ntc.getNuvaCode())//
						.setDisplay(ntc.getNuvaLabel())//
						.setEquivalence(ConceptMapEquivalence.EQUAL);
			}
		});
	}

	private String lookupDisplayForCode(CodeSystem swissmdedicCs, String code) {
		if (swissmdedicCs != null && swissmdedicCs.hasConcept()) {
			return swissmdedicCs.getConcept().stream()//
					.filter(c -> c.getCode().equals(code))//
					.findFirst()//
					.map(c -> c.getDisplay())//
					.orElse(null);
		}
		return null;
	}

	private CodeSystem getSwissmedicCodesystem() {
		CodeSystem codeSystem = null;
		try {
			URI csUrl = new URI("https://tx.fhir.ch/r4/CodeSystem/ch-vacd-swissmedic-cs?_format=json");

			codeSystem = ctx.newJsonParser().parseResource(CodeSystem.class, csUrl.toURL().openStream());
		} catch (Exception e) {
			logger.error("Error fetching Swissmedic CodeSystem: {}", e.getMessage());
		}
		return codeSystem;
	}
	
	private CodeSystem getSwisslegacyCodesystem() {
		CodeSystem codeSystem = null;
		try {
			URI csUrl = new URI("https://tx.fhir.ch/r4/CodeSystem/ch-vacd-myvaccines-cs?_format=json");

			codeSystem = ctx.newJsonParser().parseResource(CodeSystem.class, csUrl.toURL().openStream());
		} catch (Exception e) {
			logger.error("Error fetching Swissmedic CodeSystem: {}", e.getMessage());
		}
		return codeSystem;
	}
	

}
