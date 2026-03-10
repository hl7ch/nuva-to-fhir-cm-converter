/**
 * 
 */
package ch.hl7.vacd.domain;

import java.net.URI;

/**
 * 
 */
public class MigrationItems {
	private String name;
	private CmDefinition cmDefinitionFrom;
	private CmDefinition cmDefinitionTo;
	private String codesystem;
	private String valueset;
	private URI tonuva;
	private URI fromnuva;

	public MigrationItems() {

	}

	/**
	 * Method to get the name of the migration item.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name of the migration item.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method to get the codesystem of the migration item.
	 * 
	 * @return the codesystem
	 */
	public String getCodesystem() {
		return codesystem;
	}

	/**
	 * Method to set the codesystem of the migration item.
	 * 
	 * @param codesystem the codesystem to set
	 */
	public void setCodesystem(String codesystem) {
		this.codesystem = codesystem;
	}

	/**
	 * Method to get the valueset of the migration item.
	 * 
	 * @return the valueset
	 */
	public String getValueset() {
		return valueset;
	}

	/**
	 * Method to set the valueset of the migration item.
	 * 
	 * @param valueset the valueset to set
	 */
	public void setValueset(String valueset) {
		this.valueset = valueset;
	}

	/**
	 * Method to get the tonuva of the migration item.
	 * 
	 * @return the tonuva
	 */
	public URI getTonuva() {
		return tonuva;
	}

	/**
	 * Method to set the tonuva of the migration item.
	 * 
	 * @param tonuva the tonuva to set
	 */
	public void setTonuva(URI tonuva) {
		this.tonuva = tonuva;
	}

	/**
	 * Method to get the fromnuva of the migration item.
	 * 
	 * @return the fromnuva
	 */
	public URI getFromnuva() {
		return fromnuva;
	}

	/**
	 * Method to set the fromnuva of the migration item.
	 * 
	 * @param fromnuva the fromnuva to set
	 */
	public void setFromnuva(URI fromnuva) {
		this.fromnuva = fromnuva;
	}

	/**
	 * @return the cmDefinitionFrom
	 */
	public CmDefinition getCmDefinitionFrom() {
		return cmDefinitionFrom;
	}

	/**
	 * @param cmDefinitionFrom the cmDefinitionFrom to set
	 */
	public void setCmDefinitionFrom(CmDefinition cmDefinitionFrom) {
		this.cmDefinitionFrom = cmDefinitionFrom;
	}

	/**
	 * @return the cmDefinitionTo
	 */
	public CmDefinition getCmDefinitionTo() {
		return cmDefinitionTo;
	}

	/**
	 * @param cmDefinitionTo the cmDefinitionTo to set
	 */
	public void setCmDefinitionTo(CmDefinition cmDefinitionTo) {
		this.cmDefinitionTo = cmDefinitionTo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MigrationItems [name=");
		builder.append(name);
		builder.append(", cmDefinitionFrom=");
		builder.append(cmDefinitionFrom);
		builder.append(", cmDefinitionTo=");
		builder.append(cmDefinitionTo);
		builder.append(", codesystem=");
		builder.append(codesystem);
		builder.append(", valueset=");
		builder.append(valueset);
		builder.append(", tonuva=");
		builder.append(tonuva);
		builder.append(", fromnuva=");
		builder.append(fromnuva);
		builder.append("]");
		return builder.toString();
	}

}
