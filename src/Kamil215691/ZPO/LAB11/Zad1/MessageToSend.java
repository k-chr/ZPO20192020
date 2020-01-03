package Kamil215691.ZPO.LAB11.Zad1;

import java.io.Serializable;

public class MessageToSend implements Serializable {

    public enum TypeOfMessage implements Serializable{
        START,
        LOGIN,
        UPDATE,
        END
    }

    private Object content = null;
    private TypeOfMessage type;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public TypeOfMessage getType() {
        return type;
    }

    public void setType(TypeOfMessage type) {
        this.type = type;
    }
}
