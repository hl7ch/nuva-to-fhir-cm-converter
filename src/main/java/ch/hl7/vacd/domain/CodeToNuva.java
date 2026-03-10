/**
 * 
 */
package ch.hl7.vacd.domain;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Class representing the mapping from Swissmedic codes to Nuva codes. This class is used to parse the CSV file containing the mappings and to store the data in a structured way.
 * see {@link https://github.com/IVC-NUVA/NUVA?tab=readme-ov-file#code2nuvacsv}
 */
public class CodeToNuva {
	/** the code in the aligned code system */
	@CsvBindByPosition(position = 0)
	private String code;
	
	/** the equivalent concept code in NUVA */
	@CsvBindByPosition(position = 1)
	private String nuvaCode;
	
	/** the English label for this vaccine code */
	@CsvBindByPosition(position = 2)
	private String nuvaLabel;

	/** Default constructor */
	public CodeToNuva() {

	}

	/**
	 * Constructor with all fields
	 * 
	 * @param code      the code to be mapped to Nuva
	 * @param nuvaCode  the corresponding Nuva code
	 * @param nuvaLabel the label for the Nuva code
	 */
	public CodeToNuva(String code, String nuvaCode, String nuvaLabel) {
		super();
		this.code = code;
		this.nuvaCode = nuvaCode;
		this.nuvaLabel = nuvaLabel;
	}

	/**
	 * Method to get the code
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Method to set the code
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Method to get the Nuva code
	 * 
	 * @return the nuvaCode
	 */
	public String getNuvaCode() {
		return nuvaCode;
	}

	/**
	 * Method to set the Nuva code
	 * 
	 * @param nuvaCode the nuvaCode to set
	 */
	public void setNuvaCode(String nuvaCode) {
		this.nuvaCode = nuvaCode;
	}

	/**
	 * Method to get the Nuva label
	 * 
	 * @return the nuvaLabel
	 */
	public String getNuvaLabel() {
		return nuvaLabel;
	}

	/**
	 * Method to set the Nuva label
	 * 
	 * @param nuvaLabel the nuvaLabel to set
	 */
	public void setNuvaLabel(String nuvaLabel) {
		this.nuvaLabel = nuvaLabel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeToNuva [code=");
		builder.append(code);
		builder.append(", nuvaCode=");
		builder.append(nuvaCode);
		builder.append(", nuvaLabel=");
		builder.append(nuvaLabel);
		builder.append("]");
		return builder.toString();
	}

}
