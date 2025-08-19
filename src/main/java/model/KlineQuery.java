package model;

import lombok.Data;

@Data
public class KlineQuery {
  private String symbol;
  private String interval;
  private Long startTime;
  private Long endTime;
}
