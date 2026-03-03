/**
 * 
 */
package ch.hl7.vacd.domain;

import com.opencsv.bean.CsvBindByPosition;

/**
 * This class represents the mapping between Nuva codes and Swissmedic codes as defined in the CSV file
 * see {@link chttps://github.com/IVC-NUVA/NUVA#nuva2codecsv}
 */
public class NuvaToSwissmedic {
	/** the NUVA concept code */
	@CsvBindByPosition(position = 0)
	private String nuvaCode;
	
	/** the English label for this code */
	@CsvBindByPosition(position = 1)
	private String nuvaLabel;
	
	/** a IsAbstract boolean indicating if the code belongs to a real vaccine */
	@CsvBindByPosition(position = 2)
	private boolean isAbstract;
	
	/** a code in the code system that may represent this NUVA code (possibly empty if no such code exists) */
	@CsvBindByPosition(position = 3)
	private String code;
	
	/**  */
	@CsvBindByPosition(position = 4)
	private String codeLabel;
	
	/**a Best boolean indicating if this code is the best possible option for representing this NUVA concept. And if this is a Best code */
	@CsvBindByPosition(position = 5)
	private boolean best;
	
	/** a Blur value indicating how many NUVA concepts are best represented by this same code. */
	@CsvBindByPosition(position = 6)
	private int blur;
	
	/** a Equiv value indicating how many codes in the code system are exactly equivalent to the NUVA concept (they differ by a criterion outside of the NUVA scope) */
	@CsvBindByPosition(position = 7)
	private int equivalence;

	/** Default constructor */
	public NuvaToSwissmedic() {

	}

	/**
	 * Constructor with all fields
	 *
	 * @param nuvaCode    the Nuva code to be mapped
	 * @param nuvaLabel   the label for the Nuva code
	 * @param code        the corresponding code
	 * @param codeLabel   the label for the corresponding code
	 * @param best        indicates if this mapping is the best match
	 * @param blur        the blur level of the mapping
	 * @param equivalence the equivalence level of the mapping
	 * 
	 */
	public NuvaToSwissmedic(String nuvaCode, String nuvaLabel, String code, String codeLabel, boolean best, int blur,
			int equivalence) {
		super();
		this.nuvaCode = nuvaCode;
		this.nuvaLabel = nuvaLabel;
		this.code = code;
		this.codeLabel = codeLabel;
		this.best = best;
		this.blur = blur;
		this.equivalence = equivalence;
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
	 * Method to get the code label
	 * 
	 * @return the codeLabel
	 */
	public String getCodeLabel() {
		return codeLabel;
	}

	/**
	 * Method to set the code label
	 * 
	 * @param codeLabel the codeLabel to set
	 */
	public void setCodeLabel(String codeLabel) {
		this.codeLabel = codeLabel;
	}

	/**
	 * Method to check if this mapping is the best match
	 * 
	 * @return the best
	 */
	public boolean isBest() {
		return best;
	}

	/**
	 * Method to set if this mapping is the best match
	 * 
	 * @param best the best to set
	 */
	public void setBest(boolean best) {
		this.best = best;
	}

	/**
	 * Method to get the blur level of the mapping
	 * 
	 * @return the blur
	 */
	public int getBlur() {
		return blur;
	}

	/**
	 * Method to set the blur level of the mapping
	 * 
	 * @param blur the blur to set
	 */
	public void setBlur(int blur) {
		this.blur = blur;
	}

	/**
	 * Method to get the equivalence level of the mapping
	 * 
	 * @return the equivalence
	 */
	public int getEquivalence() {
		return equivalence;
	}

	/**
	 * Method to set the equivalence level of the mapping
	 * 
	 * @param equivalence the equivalence to set
	 */
	public void setEquivalence(int equivalence) {
		this.equivalence = equivalence;
	}

	/**
	 * @return the isAbstract
	 */
	public boolean isAbstract() {
		return isAbstract;
	}

	/**
	 * @param isAbstract the isAbstract to set
	 */
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NuvaToSwissmedic [nuvaCode=");
		builder.append(nuvaCode);
		builder.append(", nuvaLabel=");
		builder.append(nuvaLabel);
		builder.append(", isAbstract=");
		builder.append(isAbstract);
		builder.append(", code=");
		builder.append(code);
		builder.append(", codeLabel=");
		builder.append(codeLabel);
		builder.append(", best=");
		builder.append(best);
		builder.append(", blur=");
		builder.append(blur);
		builder.append(", equivalence=");
		builder.append(equivalence);
		builder.append("]");
		return builder.toString();
	}

	

}
