package com.github.wisniewsky00.clean_energy_charging_optimizer.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NesoClient {

    private WebClient client;

    public NesoClient() {
    }

    public GenerationMixResponse fetchGenerationMixBetweenDates(String from, String to)
    {
       return null;
    }
}
