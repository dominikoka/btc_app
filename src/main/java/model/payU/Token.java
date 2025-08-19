package model.payU;

import lombok.Data;

@Data
public class Token {
  private String access_token;
  private String token_type;
  private int expires_in;
  private String grant_type;

}
