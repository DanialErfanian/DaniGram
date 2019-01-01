package Main.Models;


public class Message {
    private String text;
    private User sender;

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
    }
}
