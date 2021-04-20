package com.cassini.test.models.api;

import lombok.Data;

@Data
public class Resource {
    Integer id;
    String name;
    Integer year;
    String color;
    String pantone_value;
}
