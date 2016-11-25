import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/25.
 */

public class SocketServer {
    public static void main(String[] args) {
        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            Socket socket = null;
            //2.调用accept()等待客户端连接
            System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址: " + ip);
            socket = serverSocket.accept();
            //3.连接后获取输入流，读取客户端信息
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            is = socket.getInputStream();     //获取输入流
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {//循环读取客户端的信息
                System.out.println("客户端发送过来的信息" + info);
            }
            socket.shutdownInput();//关闭输入流
            socket.close();
        } catch (Exception e) {

        }

    }
}
