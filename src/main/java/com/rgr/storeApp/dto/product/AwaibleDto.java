package com.rgr.storeApp.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwaibleDto {

    @NotNull
    @NotEmpty
    @Positive
    int number;
}
