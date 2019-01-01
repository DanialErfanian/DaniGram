package Main.Models;


public class Message {
    private String text;
    private User sender;

    public String getText() {
        return text;
    }

    public Message(String text, User sender) {
        this.text = text;
        this.sender = sender;
    }
}
