package com.currenthealth.sas.model.payload;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LabelValuePair {

    @NonNull
    private String label;

    @NonNull
    private String value;
}
