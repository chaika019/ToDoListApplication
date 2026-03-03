package by.chaika19.entity.dto;

import by.chaika19.entity.Record;
import by.chaika19.entity.User;

import java.util.List;

public class RecordsContainerDto {
    private final String userName;
    private final List<Record> records;
    private final int numberOfDoneRecords;
    private final int numberOfActiveRecords;

    public RecordsContainerDto(String userName, List<Record> records, int numberOfDoneRecords, int numberOfActiveRecords) {
        this.userName = userName;
        this.records = records;
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.numberOfActiveRecords = numberOfActiveRecords;
    }

    public String getUserName() {
        return userName;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfDoneRecords() {
        return numberOfDoneRecords;
    }

    public int getNumberOfActiveRecords() {
        return numberOfActiveRecords;
    }
}
