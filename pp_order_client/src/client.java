import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class client {

    public static void main(String[] args) throws IOException {
        String state = "no user";//"no user" "user" "admin" "root"
        String[] commands = {"create", "login", "logout", "delete", "addroom", "reserve_room", "show_reservations", "show_reservation"};

        Scanner sc = new Scanner(System.in);
        while (true) {
            //创建Scoket对象，并设置要访问的服务的IP和端口号
            Socket socket = new Socket("localhost", 9961);
            System.out.println("Server Connected, please enter command:");
            //返回此客户端的一个输出流
            OutputStream os = socket.getOutputStream();
            String str = sc.nextLine();
            //System.out.println(str);
            String strClient = str;
            //处理命令
            String[] prem = str.split(" ");
            if (Arrays.asList(commands).contains(prem[0])){
                //将客户要发送的信息翻译成字节文件，发送出去
                os.write(strClient.getBytes());
                //发送一个终结符，告诉服务器，已经发送完毕
                socket.shutdownOutput();
                //以下是获取服务器回写的数据
                System.out.println("Server Connected, Response is:");
                //返回此客户端的一个输入流
                InputStream is = socket.getInputStream();
                //将服务器回写的数据进行翻译，并打印在控制台
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    System.out.println(new String(bytes, 0, len));
                }
                //关闭资源
                is.close();
            } else {
                System.out.println("WRONG COMMAND");
            }


            os.close();
        }
    }
}