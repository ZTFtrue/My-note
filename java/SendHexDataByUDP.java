package com.ztftrue.sendData;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String host = "125.125.125.125";
    private final static String data = "11 11 11 11 11 11 11 11";
    private final static int port = 8080;
    private DatagramSocket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.send).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                new Thread(() -> {
                    sendData(data, port, host);
                }).start();
                break;
            default:
                break;
        }
    }

    public void sendData(String data, int port, String... hosts) {
        try {
            mSocket = new DatagramSocket();
            for (String host : hosts) {
                InetSocketAddress address = new InetSocketAddress(host, port);
                byte[] sendData = hexStrToByteArrs(data);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address);
                mSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mSocket != null) {
                mSocket.close();
            }
        }
    }

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString 字符串
     * @return 字节
     */
    public static byte[] hexStrToByteArrs(String hexString) /* throws NullPointerException */ {
        if (TextUtils.isEmpty(hexString)) {
            throw new NullPointerException("字符串为空");
        }
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];
        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }
        return bytes;
    }
}
