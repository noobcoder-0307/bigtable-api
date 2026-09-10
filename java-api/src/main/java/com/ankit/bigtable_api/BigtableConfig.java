package com.ankit.bigtable_api;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BigtableConfig {

    @Bean(destroyMethod = "close")
    public BigtableDataClient bigtableDataClient() throws IOException {
        BigtableDataSettings settings = BigtableDataSettings
                .newBuilderForEmulator("localhost", 8086)
                .setProjectId("test-project")
                .setInstanceId("test-instance")
                .build();

        return BigtableDataClient.create(settings);
    }
}