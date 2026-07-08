package spyrosoft.testproject.calculator;

import org.junit.jupiter.api.Test;
import spyrosoft.testproject.dto.energyMixRequest.FuelMixEntry;
import spyrosoft.testproject.dto.energyMixRequest.GenerationInterval;
import spyrosoft.testproject.dto.chargingResponse.ChargingWindowResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnergyMixCalculatorTest {

	private final EnergyMixCalculator calculator = new EnergyMixCalculator();

	@Test
	void testCalculations() {
		List<GenerationInterval> testIntervals = new ArrayList<>();
		Instant baseline = Instant.parse("2020-01-01T00:00:00Z");
		for (int i = 0;i<144;i++){
			List<FuelMixEntry> fuels = new ArrayList<>();
			if(i>10 && i<20){
				fuels.add(new FuelMixEntry("coal",10));
				fuels.add(new FuelMixEntry("nuclear",90));

			}else{
				fuels.add(new FuelMixEntry("coal",100));

			}
			testIntervals.add(new GenerationInterval(baseline.plus(i*30, ChronoUnit.MINUTES),baseline.plus((i+1)*30, ChronoUnit.HOURS),fuels));
		}
		System.out.println(testIntervals);

		ChargingWindowResponse result = calculator.findBestChargingWindow(testIntervals, 3);

		assertEquals(baseline.plus(11*30, ChronoUnit.MINUTES),result.start());
		assertEquals(baseline.plus(17*30, ChronoUnit.MINUTES),result.end());
		assertEquals(3,result.windowHours());
		assertEquals(90,result.averageCleanEnergyPercentage());

	}

}
