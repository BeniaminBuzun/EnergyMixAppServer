package spyrosoft.testproject.service;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClient;
import spyrosoft.testproject.calculator.EnergyMixCalculator;
import spyrosoft.testproject.dto.energyMixRequest.DayEnergyMix;
import spyrosoft.testproject.dto.energyMixRequest.GenerationInterval;
import spyrosoft.testproject.dto.response.ChargingWindowResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargingWindowService {

	private final EnergyMixCalculator energyMixCalculator;
	private final RestClient restClient;

	DateTimeFormatter formatter;

	public ChargingWindowService( EnergyMixCalculator energyMixCalculator) {
		this.energyMixCalculator = energyMixCalculator;
		this.restClient = RestClient.create();

		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'")
				.withZone(ZoneId.of("UTC"));

	}

	public ChargingWindowResponse findBestWindow(int hours) {
		getDataFromApi();
		return energyMixCalculator.findBestChargingWindow(getDataFromApi(),hours);
	}
	private List<GenerationInterval> getDataFromApi (){
		ArrayList<DayEnergyMix> data = new ArrayList<>();
		for(int i=0;i<3;i++) {
			String formattedDate = formatter.format(Instant.now().plus(i, ChronoUnit.DAYS));
			System.out.println(formattedDate);
			data.add(restClient.get()
					.uri("https://api.carbonintensity.org.uk/generation/" + formattedDate + "/pt24h")
					.retrieve()
					.body(DayEnergyMix.class));
		}
		return data.stream().flatMap(dayValue->dayValue.data().stream()).collect(Collectors.toList());

	}
}
