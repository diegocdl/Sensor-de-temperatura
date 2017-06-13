package io.github.diegocdl.sesnor_temperatura.data;

/**
 * Created by diego on 6/7/2017.
 */

public interface CommunicationInterface {
    public void reportConnectionError(String error);
    public void dataReceived(TempRegister tr);
}
