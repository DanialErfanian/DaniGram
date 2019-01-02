package Main.Models;


import com.vdurmont.emoji.EmojiParser;

public class Message {
    private String text;
    private User sender;

    public String getText() {
        return text;
    }

    public Message(String text, User sender) {
        text = EmojiParser.parseToUnicode(text);
        this.text = text;
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }
}
