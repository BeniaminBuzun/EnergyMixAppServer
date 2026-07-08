package spyrosoft.testproject.dto.response;

import java.time.Instant;

public record ChargingWindowResponse(
		Instant start,
		Instant end,
		double averageCleanEnergyPercentage,
		int windowHours) {
}
