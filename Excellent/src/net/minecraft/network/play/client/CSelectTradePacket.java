package net.minecraft.network.play.client;

import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

import java.io.IOException;

public class CSelectTradePacket implements IPacket<IServerPlayNetHandler>
{
    private int field_210354_a;

    public CSelectTradePacket()
    {
    }

    public CSelectTradePacket(int p_i49545_1_)
    {
        this.field_210354_a = p_i49545_1_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.field_210354_a = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.field_210354_a);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IServerPlayNetHandler handler)
    {
        handler.processSelectTrade(this);
    }

    public int func_210353_a()
    {
        return this.field_210354_a;
    }
}
