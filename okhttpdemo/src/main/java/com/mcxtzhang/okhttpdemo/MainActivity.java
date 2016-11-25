package com.mcxtzhang.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        //1.创建客户端Socket，指定服务器地址和端口
                        try {
                            Socket socket = new Socket("10.0.12.90", 12345);
                            //2.获取输出流，向服务器端发送信息
                            OutputStream os = socket.getOutputStream();//字节输出流
                            PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
                            //获取客户端的IP地址
                            InetAddress address = InetAddress.getLocalHost();
                            String ip = address.getHostAddress();
                            pw.write("客户端：~" + ip + "~ 接入服务器！！");
                            pw.flush();
                            socket.shutdownOutput();//关闭输出流
                            socket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
