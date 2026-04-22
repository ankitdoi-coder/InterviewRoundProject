package com.kj.interviewround.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
