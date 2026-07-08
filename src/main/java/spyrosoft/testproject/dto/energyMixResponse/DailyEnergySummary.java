package spyrosoft.testproject.dto.energyMixResponse;

import java.util.Map;

public record DailyEnergySummary(Map<String, Double> sources) {
}
