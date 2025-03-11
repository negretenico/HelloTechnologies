package com.hellotechnologies.realtionalDatabase.models.row;

import com.hellotechnologies.realtionalDatabase.models.column.ColumnDefinition;
import org.yaml.snakeyaml.util.Tuple;

import java.util.List;

public record RowDefinition(List<Tuple<ColumnDefinition, Object>> row) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RowDefinition comparing = (RowDefinition) obj;
        if (comparing.row.size() != this.row.size()) {
            return false;
        }
        return this.row.equals(comparing.row);
    }

    @Override
    public int hashCode() {
        return this.row.stream().mapToInt(s -> s._1().name().hashCode() + s._1().datatype().hashCode()).sum();
    }
}
