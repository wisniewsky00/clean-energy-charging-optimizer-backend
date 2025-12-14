package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixEntry;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixResponse;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixSlot;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.NesoClient;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.EnergyMixSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;

public class ChargingOptimizationServiceTest {

    private NesoClient nesoClient;
    private ChargingOptimizationService service;

    @BeforeEach
    void setUp() {
        nesoClient = Mockito.mock(NesoClient.class);
        service = new ChargingOptimizationService(nesoClient);
    }

    @Test
    void shouldReturnWindowWithHighestValue()
    {
        GenerationMixResponse response = new GenerationMixResponse();
        response.setData(List.of(
                new GenerationMixSlot(
                        "2025-12-14T00:00Z",
                        "2025-12-14T00:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 80),
                                new GenerationMixEntry("gas", 20)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T00:30Z",
                        "2025-12-14T01:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 80),
                                new GenerationMixEntry("gas", 20)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T01:00Z",
                        "2025-12-14T01:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 30),
                                new GenerationMixEntry("gas", 70)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T01:30Z",
                        "2025-12-14T02:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 30),
                                new GenerationMixEntry("gas", 70)
                        )
                )
        ));

        Mockito.when(nesoClient.fetchGenerationMixBetweenDates(anyString(), anyString()))
                .thenReturn(response);

        EnergyMixSummary result = service.calculateOptimizedChargingWindow(1);

        assertThat(result.getAvgCleanEnergy()).isEqualTo(80.0);
        assertThat(result.getFrom()).isEqualTo(OffsetDateTime.parse("2025-12-14T00:00Z"));
        assertThat(result.getTo()).isEqualTo(OffsetDateTime.parse("2025-12-14T01:00Z"));
    }

    @Test
    void shouldReturnCorrectResultForThreeHourWindow() {
        GenerationMixResponse response = new GenerationMixResponse();
        response.setData(List.of(
                new GenerationMixSlot(
                        "2025-12-14T00:00Z",
                        "2025-12-14T00:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T00:30Z",
                        "2025-12-14T01:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T01:00Z",
                        "2025-12-14T01:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T01:30Z",
                        "2025-12-14T02:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T02:00Z",
                        "2025-12-14T02:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T02:30Z",
                        "2025-12-14T03:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T03:00Z",
                        "2025-12-14T03:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 20),
                                new GenerationMixEntry("gas", 80)
                        )
                )
        ));

        Mockito.when(nesoClient.fetchGenerationMixBetweenDates(anyString(), anyString()))
                .thenReturn(response);

        EnergyMixSummary result = service.calculateOptimizedChargingWindow(3);
        assertThat(result.getAvgCleanEnergy()).isEqualTo(70.0);
    }

    @Test
    void shouldThrowBadRequestForInvalidHours()
    {
        Throwable throwable = catchThrowable(() ->
                service.calculateOptimizedChargingWindow(0)
        );

        assertThat(throwable)
                .isInstanceOf(ResponseStatusException.class);

        ResponseStatusException ex = (ResponseStatusException) throwable;

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(ex.getReason())
                .isEqualTo("chargingHoursLength must be between 1 and 6");
    }

    @Test
    void shouldThrowExceptionWhenNotEnoughSlotsForWindow() {

        GenerationMixResponse response = new GenerationMixResponse();
        response.setData(List.of(
                new GenerationMixSlot(
                        "2025-12-14T00:00Z",
                        "2025-12-14T00:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T00:30Z",
                        "2025-12-14T01:00Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                ),
                new GenerationMixSlot(
                        "2025-12-14T01:00Z",
                        "2025-12-14T01:30Z",
                        List.of(
                                new GenerationMixEntry("wind", 70),
                                new GenerationMixEntry("gas", 30)
                        )
                )
        ));

        Mockito.when(nesoClient.fetchGenerationMixBetweenDates(anyString(), anyString()))
                .thenReturn(response);

        Throwable throwable = catchThrowable(() ->
                service.calculateOptimizedChargingWindow(2)
        );

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Not enough forecast data to calculate optimized charging window");
    }

}

