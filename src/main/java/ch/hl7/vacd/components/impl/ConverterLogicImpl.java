/**
 * 
 */
package ch.hl7.vacd.components.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ConceptMap.ConceptMapGroupComponent;
import org.hl7.fhir.r4.model.ConceptMap.SourceElementComponent;
import org.hl7.fhir.r4.model.Enumerations.ConceptMapEquivalence;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.UriType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ch.hl7.vacd.components.ConverterLogic;
import ch.hl7.vacd.components.NuvaToSwissmedicParser;
import ch.hl7.vacd.components.SwissmedicToNuvaParser;
import ch.hl7.vacd.domain.CodeToNuva;
import ch.hl7.vacd.domain.MigrationItems;
import ch.hl7.vacd.domain.NuvaToCode;

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

	@Value("${application.nuva.valueset}")
	private String nuvaValueSetUrl;
	@Value("${application.nuva.codesystem}")
	private String nuvaCodeSystemUrl;

	@Autowired
	private FhirContext fhirContext;

	@Autowired
	private IGenericClient fhirClient;

	@Override
	public void convertNuvaToCode(File destinationDir, MigrationItems item) throws Exception {
		List<NuvaToCode> nuvaToCodeList = nuvaToCodeParser
				.parseCsv(new org.springframework.core.io.InputStreamResource(item.getFromnuva().toURL().openStream()));
		logger.info("NuvaToCodes: {}", nuvaToCodeList.size());

		ConceptMap cm = new ConceptMap();
		cm.setId(item.getCmDefinitionFrom().getId());
		cm.setUrl(item.getCmDefinitionFrom().getUrl());
		cm.setName(item.getCmDefinitionFrom().getName());
		cm.setTitle(item.getCmDefinitionFrom().getTitle());
		cm.setDescription(item.getCmDefinitionFrom().getDescription());
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType(nuvaValueSetUrl));
		cm.setTarget(new UriType(item.getValueset()));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource(nuvaCodeSystemUrl);
		group.setTarget(item.getCodesystem());

		nuvaToCode(nuvaToCodeList, group, item.getCodesystem(), item.getName() + "-");

		writeToFile(cm, new File(destinationDir, item.getCmDefinitionFrom().getId() + ".json"));
	}

	@Override
	public void convertCodeToNuva(File destinationDir, MigrationItems item) throws Exception {
		List<CodeToNuva> codeToNuvaList = codeToNuvaParser
				.parseCsv(new org.springframework.core.io.InputStreamResource(item.getTonuva().toURL().openStream()));
		logger.info("CodeToNuvas: {}", codeToNuvaList.size());

		ConceptMap cm = new ConceptMap();
		cm.setId(item.getCmDefinitionTo().getId());
		cm.setUrl(item.getCmDefinitionTo().getUrl());
		cm.setName(item.getCmDefinitionTo().getName());
		cm.setTitle(item.getCmDefinitionTo().getTitle());
		cm.setDescription(item.getCmDefinitionTo().getDescription());
		cm.setStatus(PublicationStatus.ACTIVE);
		cm.setExperimental(false);
		cm.setSource(new UriType(item.getValueset()));
		cm.setTarget(new UriType(nuvaValueSetUrl));
		ConceptMapGroupComponent group = cm.addGroup();
		group.setSource(item.getCodesystem());
		group.setTarget(nuvaCodeSystemUrl);

		codeToNuva(codeToNuvaList, group, item.getCodesystem(), item.getName() + "-");

		writeToFile(cm, new File(destinationDir, item.getCmDefinitionTo().getId() + ".json"));
	}

	private void writeToFile(ConceptMap cm, File file) throws IOException {
		String cmJson = fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(cm);
		logger.info("Generated ConceptMap JSON:\n{}", cmJson);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(cmJson.getBytes());
		fos.close();
	}

	private void nuvaToCode(List<NuvaToCode> nuvaToCodeList, ConceptMapGroupComponent group, String codeSystemUrl,
			String replacePrefix) {

		SourceElementComponent element = null;
		for (NuvaToCode ntc : nuvaToCodeList) {
			if (!ntc.isAbstract() && !StringUtils.isEmpty(ntc.getCode())) {
				String code = ntc.getCode().replace(replacePrefix, "");
				String display = lookupDisplayForCode(codeSystemUrl, code);
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

	private void codeToNuva(List<CodeToNuva> codeToNuvaList, ConceptMapGroupComponent group, String codeSystemUrl,
			String replacePrefix) {

		codeToNuvaList.forEach(ntc -> {
			String code = ntc.getCode().replace(replacePrefix, "");
			String display = lookupDisplayForCode(codeSystemUrl, code);
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

	@Override
	public String lookupDisplayForCode(String system, String code) {
		// http://hl7.org/fhir/operation-codesystem-lookup.html
		// https://tx.fhir.ch/r4/CodeSystem/$lookup?code=1003499009&system=http://snomed.info/sct
		try {
			Parameters parameters = new Parameters();
			parameters.getMeta().addProfile("http://hl7.org/fhir/OperationDefinition/CodeSystem-lookup");
			parameters.setId(UUID.randomUUID().toString());
			parameters.addParameter().setName("code").setValue(new CodeType(code));
			parameters.addParameter().setName("system").setValue(new UriType(system));
			Parameters ref = fhirClient.operation().onType(CodeSystem.class).named("lookup").withParameters(parameters)
					.execute();

			if (ref != null && ref.hasParameter()) {
				return ref.getParameter().stream()//
						.filter(p -> p.getName().equals("display") && p.hasValue())//
						.map(p -> p.getValue().primitiveValue())//
						.findFirst()//
						.orElse(null);
			}
		} catch (Exception e) {
			logger.error("Error looking up display for code: " + code + " in system: " + system, e);
		}

		return null;
	}
}
