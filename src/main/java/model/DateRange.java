package model;

import lombok.Data;

import java.time.Instant;

@Data
public class DateRange {
  String startDate;
  String endDate;
  String Interwal;
  String Symbol;


  public DateRange(String startDate, String endDate, String Interwal,String Symbol) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.Interwal = Interwal;
    this.Symbol = Symbol;
  }


  public long getStartDateStamp() {
    String isoString = startDate;
    Instant instant = Instant.parse(isoString);
    return instant.toEpochMilli();
  }

  public long getEndDateStamp() {
    String isoString = endDate;
    Instant instant = Instant.parse(isoString);
    return instant.toEpochMilli();
  }
}
