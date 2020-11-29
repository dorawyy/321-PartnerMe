package com.example.myapplication;

import java.util.Collection;

class MessageResult {
    private Message[] chatLog;

    public Collection<? extends Message> getChat(){ return this.chatLog; }
}
