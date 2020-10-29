package com.wlsite.service.client.doman;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Person implements Serializable {
    private static final long serialVersionUID = 4252937789510802175L;
    private Long id;
    private String name;
}
