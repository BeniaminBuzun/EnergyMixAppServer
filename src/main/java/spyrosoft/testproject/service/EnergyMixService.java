package spyrosoft.testproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import spyrosoft.testproject.calculator.EnergyMixCalculator;
import spyrosoft.testproject.dto.energyMixRequest.DayEnergyMix;
import spyrosoft.testproject.dto.energyMixRequest.FuelMixEntry;
import spyrosoft.testproject.dto.energyMixRequest.GenerationInterval;
import spyrosoft.testproject.dto.energyMixResponse.DailyEnergySummary;
import spyrosoft.testproject.dto.energyMixResponse.EnergyMixResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnergyMixService {

	private final EnergyMixCalculator energyMixCalculator;
	private final RestClient restClient;
	DateTimeFormatter formatter;
	public EnergyMixService(EnergyMixCalculator energyMixCalculator) {
		this.energyMixCalculator = energyMixCalculator;
		this.restClient = RestClient.create();
		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'")
				.withZone(ZoneId.of("UTC"));
	}

	public EnergyMixResponse getThreeDayMix() {
		EnergyMixResponse response = new EnergyMixResponse(new ArrayList<>());
		for(int i=0;i<3;i++){
			String formattedDate = formatter.format(Instant.now().truncatedTo(ChronoUnit.DAYS).plus(i, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS));

			DayEnergyMix result = restClient.get()
				.uri("https://api.carbonintensity.org.uk/generation/"+formattedDate+"/pt24h")
				.retrieve()
				.body(DayEnergyMix.class);

			Map<String, Double> finalResults = result.data().stream().flatMap(timeInterval -> timeInterval.generationmix().stream()).collect(Collectors.groupingBy(
					FuelMixEntry::fuel,
					Collectors.averagingDouble(FuelMixEntry::perc)
			));

			response.days().add(new DailyEnergySummary(finalResults));
		}
		return response;
	}


}
