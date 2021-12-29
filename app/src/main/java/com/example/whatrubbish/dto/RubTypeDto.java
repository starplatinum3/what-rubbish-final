package com.example.whatrubbish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RubTypeDto {
    String cityName;
    String rubName;
}
