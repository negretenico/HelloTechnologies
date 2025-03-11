package com.hellotechnologies.realtionalDatabase.models;

import com.hellotechnologies.realtionalDatabase.models.row.RowDefinition;

public record TableDefinition(String name, RowDefinition row) {
}
