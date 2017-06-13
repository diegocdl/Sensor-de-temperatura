package io.github.diegocdl.sesnor_temperatura;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import io.github.diegocdl.sesnor_temperatura.data.CommunicationInterface;
import io.github.diegocdl.sesnor_temperatura.data.TempRegister;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final CommunicationInterface commHandler;
    private final Handler mainUIHandler;

    public ConnectThread(BluetoothDevice device, CommunicationInterface commHandler, Handler mainUIHandler)  throws IOException{
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp;
        mmDevice = device;
        this.commHandler = commHandler;
        this.mainUIHandler = mainUIHandler;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        // MY_UUID is the app's UUID string, also used by the server code
        tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        mmSocket = tmp;
    }

    public void run() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();

        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();

            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
        manageConnectedSocket(mmSocket);
    }

    private void manageConnectedSocket(BluetoothSocket mmSocket) {
        ConnectedThread ct = new ConnectedThread(mmSocket, commHandler);
        ct.start();
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            commHandler.reportConnectionError("Error abriendo el socket");
        }
    }

    class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final CommunicationInterface commHandler;

        public ConnectedThread(BluetoothSocket socket, CommunicationInterface commHandler) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            this.commHandler = commHandler;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                commHandler.reportConnectionError("Error abriendo los I/O Streams");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[24];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    char ch;
                    StringBuilder strBuff = new StringBuilder();
                    while((ch = (char)mmInStream.read()) != '\n') {
                        strBuff.append(ch);
                    }
                    Log.e("Bluetooth rv", strBuff.toString());
                    if(mainUIHandler != null) {
                        Message m = mainUIHandler.obtainMessage();
                        m.obj = new TempRegister(strBuff.toString());
                        mainUIHandler.sendMessage(m);
                    }
//                    commHandler.dataReceived(new TempRegister("", "", strBuff.toString()));
                    // Send the obtained bytes to the UI activity
//                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
//                        .sendToTarget();
                } catch (IOException e) {
                    commHandler.reportConnectionError("Error en la comunicacion");
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
}

