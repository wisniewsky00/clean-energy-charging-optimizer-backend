package com.github.wisniewsky00.clean_energy_charging_optimizer.service;

import com.github.wisniewsky00.clean_energy_charging_optimizer.client.GenerationMixResponse;
import com.github.wisniewsky00.clean_energy_charging_optimizer.client.NesoClient;
import com.github.wisniewsky00.clean_energy_charging_optimizer.dto.DailyEnergyMixSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnergyMixService {

    private final NesoClient nesoClient;

    public EnergyMixService(NesoClient nesoClient) {
        this.nesoClient = nesoClient;
    }

    public List<DailyEnergyMixSummary> calculateEnergyMixSummary(int days) {

        OffsetDateTime startingDate = getStartingDate();
        List<DailyEnergyMixSummary> dailyEnergyMixSummary = new ArrayList<>();

        for (int i = 1; i <= 1; i++) {
            OffsetDateTime endingDate = getEndingDate(startingDate);
            GenerationMixResponse response = nesoClient.fetchGenerationMixBetweenDates(startingDate.toString(), endingDate.toString());

            response.getData().forEach(generationMixSlot -> {

            });

            startingDate = startingDate.plusDays(1);
        }

        return List.copyOf(null);
    }

    private OffsetDateTime getStartingDate() {
        OffsetDateTime startOfDayUtc = LocalDate.now(ZoneOffset.UTC).atStartOfDay().plusMinutes(30).atOffset(ZoneOffset.UTC);
        startOfDayUtc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"));
        return startOfDayUtc;
    }

    private OffsetDateTime getEndingDate(OffsetDateTime startOfDayUtc) {
        return startOfDayUtc.minusMinutes(30).plusDays(1);
    }
}

//        response.getData().forEach(dailyEnergyMixSummary -> {
//            System.out.println("GenerationMixResponse: ");
//            System.out.println("\tGenerationMixSlot:");
//            System.out.println("\tFrom: " + dailyEnergyMixSummary.getFrom());
//            System.out.println("\tTo: " + dailyEnergyMixSummary.getTo());
//            System.out.println("\t\tGenerationMixEntries: ");
//            dailyEnergyMixSummary.getGenerationmix().forEach(generationMixEntry -> {
//                System.out.println("\t\t\tfuel: " +  generationMixEntry.getFuel());
//                System.out.println("\t\t\tperc: " + generationMixEntry.getPerc());
//            });
//            System.out.println("-----------------------------------------------------");
//        });