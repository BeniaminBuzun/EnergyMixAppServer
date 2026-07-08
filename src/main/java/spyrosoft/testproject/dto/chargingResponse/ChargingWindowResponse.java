package spyrosoft.testproject.dto.chargingResponse;

import java.time.Instant;

public record ChargingWindowResponse(
		Instant start,
		Instant end,
		double averageCleanEnergyPercentage,
		int windowHours) {
}
