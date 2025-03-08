package com.hellotechnologies.realtionalDatabase.models;

import com.hellotechnologies.realtionalDatabase.models.column.ColumnDefinition;

import java.util.List;
import java.util.Map;

public record TableDefinition(String name, List<Map<String, ColumnDefinition>> columns) {
}
