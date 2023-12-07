package org.example;

import lombok.Data;

@Data
public class Validation {

    private  boolean valid;

    public Validation(boolean valid){
        this.valid=valid;
    }
}
