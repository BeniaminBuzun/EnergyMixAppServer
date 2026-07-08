package spyrosoft.testproject.dto.energyMixResponse;

import java.util.List;
import java.util.Map;

public record EnergyMixResponse(List<DailyEnergySummary> days) {
}
