package com.topup.database.model.message;

public class Message {
    private String message;

    public Message(String textMessage){
        this.message = textMessage;
    }

    // Setter
    public void setMessage(String textMessage){
        this.message = textMessage;
    }

    // Getter
    public String getMessage(){
        return message;
    }
}
