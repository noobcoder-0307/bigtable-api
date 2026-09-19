package com.ankit.bigtable_api;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.TableId;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RowController {

    private final BigtableDataClient dataClient;

    public RowController(BigtableDataClient dataClient) {
        this.dataClient = dataClient;
    }

    @GetMapping("/rows/{rowKey}")
    public Map<String, String> getRow(@PathVariable String rowKey) {
        Row row = dataClient.readRow(TableId.of("my-table"), rowKey);

        if (row == null) {
            throw new RowNotFoundException("Row not found: " + rowKey);
        }

        Map<String, String> result = new HashMap<>();
        for (RowCell cell : row.getCells()) {
            result.put(cell.getQualifier().toStringUtf8(), cell.getValue().toStringUtf8());
        }
        return result;
    }

    @GetMapping("/rows")
    public List<Map<String, String>> scanRows(
            @RequestParam String prefix,
            @RequestParam(defaultValue = "10") int limit) {

        Query query = Query.create(TableId.of("my-table"))
                .prefix(prefix)
                .limit(limit);

        List<Map<String, String>> results = new ArrayList<>();
        for (Row row : dataClient.readRows(query)) {
            Map<String, String> item = new HashMap<>();
            item.put("rowKey", row.getKey().toStringUtf8());
            for (RowCell cell : row.getCells()) {
                item.put(cell.getQualifier().toStringUtf8(), cell.getValue().toStringUtf8());
            }
            results.add(item);
        }
        return results;
    }

    @ExceptionHandler(RowNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRowNotFound(RowNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}

class RowNotFoundException extends RuntimeException {
    public RowNotFoundException(String message) {
        super(message);
    }
}