package com.saas.entity.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Patient{
    @Id
    @Column("patient_id")
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    protected Patient(){}
}
