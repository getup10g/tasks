package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    private String toCC;
    private String subject;
    private String message;

    public Mail(){}
    public Mail(String mailTo,String subject, String message){
        this.mailTo=mailTo;
        this.subject=subject;
        this.message=message;
    }

}
