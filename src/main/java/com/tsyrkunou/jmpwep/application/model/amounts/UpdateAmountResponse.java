package com.tsyrkunou.jmpwep.application.model.amounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAmountResponse {
    private String ownerName;
    private String ownerBalance;
}
