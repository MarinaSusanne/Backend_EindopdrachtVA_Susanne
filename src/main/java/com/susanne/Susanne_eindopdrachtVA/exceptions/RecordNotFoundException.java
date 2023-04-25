package com.susanne.Susanne_eindopdrachtVA.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(){
        super();
    }

    public RecordNotFoundException (String message){
        super(message);
    }
}
