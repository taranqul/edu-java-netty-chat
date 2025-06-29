package edu.taranqul.chat.server;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Dependencies deps = new Dependencies();
        new Server(8080, deps).start();
    } 
}
