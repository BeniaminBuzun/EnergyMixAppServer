package spyrosoft.testproject.dto.energyMixRequest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public record GenerationInterval(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmX", timezone = "UTC")
        Instant from,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmX", timezone = "UTC")
        Instant to,
        List<FuelMixEntry> generationmix) {
}
