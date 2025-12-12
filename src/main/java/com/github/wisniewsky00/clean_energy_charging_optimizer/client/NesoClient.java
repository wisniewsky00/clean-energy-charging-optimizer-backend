package com.github.wisniewsky00.clean_energy_charging_optimizer.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NesoClient {

    private final WebClient webClient;

    public NesoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GenerationMixResponse fetchGenerationMixBetweenDates(String from, String to)
    {
       return null;
    }
}
