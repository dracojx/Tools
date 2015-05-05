package exceltoxsd.v5;

public class Restriction {
	private Integer minLength;
	private Integer maxLength;
	private Integer totalDigits;
	private Integer fractionDigits;
	
	public Restriction(String type, Integer max, Integer min) {
		if(Constants.TYPE_STRING.equalsIgnoreCase(type)) {
			maxLength = max;
			minLength = min;
		}else if(Constants.TYPE_DECIMAL.equalsIgnoreCase(type)) {
			totalDigits = max;
			fractionDigits = min;
		}
	}
	
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public Integer getTotalDigits() {
		return totalDigits;
	}
	public void setTotalDigits(Integer totalDigits) {
		this.totalDigits = totalDigits;
	}
	public Integer getFractionDigits() {
		return fractionDigits;
	}
	public void setFractionDigits(Integer fractionDigits) {
		this.fractionDigits = fractionDigits;
	}
}
