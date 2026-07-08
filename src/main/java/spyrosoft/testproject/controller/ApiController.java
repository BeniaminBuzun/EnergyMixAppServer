package spyrosoft.testproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import spyrosoft.testproject.dto.energyMixResponse.EnergyMixResponse;
import spyrosoft.testproject.dto.chargingResponse.ChargingWindowResponse;
import spyrosoft.testproject.service.ChargingWindowService;
import spyrosoft.testproject.service.EnergyMixService;

@RestController
@RequestMapping("/api")
public class ApiController {

	private final EnergyMixService energyMixService;
	private final ChargingWindowService chargingWindowService;

	public ApiController(EnergyMixService energyMixService, ChargingWindowService chargingWindowService) {
		this.energyMixService = energyMixService;
		this.chargingWindowService = chargingWindowService;
	}

//	endpoint returns predicted energy mix for next 3 days
	@GetMapping("/energy-mix")
	public EnergyMixResponse getEnergyMix() {
		return energyMixService.getThreeDayMix();

	}

//	endpoint finds best charging window set length in next 3 days
	@GetMapping("/charging-window")
	public ChargingWindowResponse getChargingWindow(
			@RequestParam @Min(1) @Max(6) int hours) {
		System.out.println("asdf");

		return chargingWindowService.findBestWindow(hours);
	}

}
