package spyrosoft.testproject.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import spyrosoft.testproject.dto.energyMixRequest.FuelMixEntry;
import spyrosoft.testproject.dto.energyMixRequest.GenerationInterval;
import spyrosoft.testproject.dto.response.ChargingWindowResponse;
import spyrosoft.testproject.dto.energyMixResponse.EnergyMixResponse;

@Component
public class EnergyMixCalculator {

	public static final Set<String> CLEAN_FUELS = Set.of("biomass", "nuclear", "hydro", "wind", "solar");

//	function for calculating clean energy percentage
	public double cleanEnergyPercentage(List<FuelMixEntry> mix) {
		return mix.stream()
				.filter(f -> CLEAN_FUELS.contains(f.fuel()))
				.mapToDouble(FuelMixEntry::perc)
				.sum();
	}


//	function for finding best charging window
	public ChargingWindowResponse findBestChargingWindow(List<GenerationInterval> intervals, int hours) {
		ArrayList<Double> cleanPercentages = new ArrayList<>();
		double sum = 0;
		double max = 0;
		int pos = 0;
		for (GenerationInterval element:intervals){
			cleanPercentages.add(cleanEnergyPercentage(element.generationmix()));
		}
		for(int i = 0; i<cleanPercentages.toArray().length;i++){
			if (i<hours*2){
				sum += cleanPercentages.get(i);
			}else{

				if(sum>max){
					max = sum;
					pos = i;
				}
				sum = sum - cleanPercentages.get(i-hours*2) + cleanPercentages.get(i);
			}
		}
//		if there is no clean energy prevent error
		if (pos<hours){
			pos = hours*2;
		}
		System.out.println("POS"+pos);
		System.out.println(max);
		return new ChargingWindowResponse(intervals.get(pos-hours*2).from(),intervals.get(pos).from(),max/(hours*2),hours);
	}


}
