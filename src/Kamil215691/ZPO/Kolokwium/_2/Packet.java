package Kamil215691.ZPO.Kolokwium._2;

import java.io.Serializable;

public class Packet implements Serializable {
    public enum Type_Of_Packet{
        COMMAND(0xDDDDDDDD),
        FILE_PACKET(0xFFFFFFFF),
        ID(0xBBBBBBBB),
        CLOSE_FILE(0x99999999);
        private int partValue;
        Type_Of_Packet(int val){
            partValue = val;
        }
        int INT_VALUE(){return partValue;}
    }

    private Type_Of_Packet type;
    private byte[] pkg = null;

    public Type_Of_Packet getType() {
        return type;
    }

    public byte[] getPkg() {
        return pkg;
    }

    public Packet(Type_Of_Packet type, byte... bytes){
        this.type = type;
        this.pkg = bytes;
    }
}
