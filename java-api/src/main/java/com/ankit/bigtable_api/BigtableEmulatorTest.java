package com.ankit.bigtable_api;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowMutation;

import java.io.IOException;

public class BigtableEmulatorTest {

    public static void main(String[] args) throws IOException {
        BigtableDataSettings settings = BigtableDataSettings
                .newBuilderForEmulator("localhost", 8086)
                .setProjectId("test-project")
                .setInstanceId("test-instance")
                .build();

        try (BigtableDataClient dataClient = BigtableDataClient.create(settings)) {

            RowMutation mutation = RowMutation.create("my-table", "row1")
                    .setCell("cf1", "name", "Ankit");
            dataClient.mutateRow(mutation);
            System.out.println("Row written.");

            Row row = dataClient.readRow("my-table", "row1");
            System.out.println("Row read back: " + row);
        }
    }
}