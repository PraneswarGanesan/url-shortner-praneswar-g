package com.shortner.dto;
import java.util.*;

import lombok.Data;

@Data
public class RegisterRequest {
   private String username;
   private String email;
   private Set<String> role;
   private String password;
}
