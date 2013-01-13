package dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DistributionDto {

	@XmlElement
	private List<Integer> distribution;

	public DistributionDto(Integer[] gradesDistribution) {
		this.distribution = new ArrayList<Integer>(
				Arrays.asList(gradesDistribution));
	}
	
	public DistributionDto() {
	}


	public List<Integer> getDistribution() {
		return this.distribution;
	}
}
