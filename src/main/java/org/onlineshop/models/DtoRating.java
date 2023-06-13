package org.onlineshop.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoRating {
    private long id;
    private double finalScore;
    private Product product;

}
