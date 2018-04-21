package com.crud.tasks.domain.createdTrelloCard;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Badges {
    private int votes;
    private AttachmentsByType attachmentsByType;

    public Badges(){

    }
}

