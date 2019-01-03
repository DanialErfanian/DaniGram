package Main.Models;


import Main.Utils.Utils;
import com.vdurmont.emoji.EmojiParser;

import java.io.File;

public class Message {
    public enum Type {
        TEXT, IMAGE, FILE
    }

    private User sender;
    private Type type;
    private byte[] data;
    private String fileName; // if you are sending a file (image, gif, .. anything else text) need to set it

    public String getText() throws InvalidTypeException {
        if (this.type != Type.TEXT)
            throw new InvalidTypeException(String.format("Message have %s type and get text haven't mean", this.type));
        return new String(data);
    }

    public byte[] getData() {
        return data;
    }

    public Type getType() {
        return type;
    }

    public Message(String text, User sender) {
        text = EmojiParser.parseToUnicode(text);
        this.data = text.getBytes();
        this.type = Type.TEXT;
        this.sender = sender;
    }

    public Message(User sender, Type type, byte[] data, String fileName) {
        this.sender = sender;
        this.type = type;
        this.data = data;
        this.fileName = fileName;
    }

    public User getSender() {
        return sender;
    }

    public static Type getFileType(File file) {
        String extension = Utils.getFileExtention(file);
        if (extension == null)
            return null;
        switch (extension) {
            case "jpg":
            case "gif":
            case "png":
                return Type.IMAGE;
            default:
                return Type.FILE;
        }
    }

    public String getFileName() {
        return fileName;
    }
}
