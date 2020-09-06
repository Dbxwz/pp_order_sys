import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class sever_main {
    private static void handle_order(String order){

    }
    public static void main(String[] args) throws IOException {
        String state = "no user";//"no user" "user" "admin" "root"
        String user_name = "";

        //创建ServiceScoket对象，并设置端口号为9961
        ServerSocket ss = new ServerSocket(9961);
        Scanner sc = new Scanner(System.in);
        //创建一个无限循环，来保证可以循环发送和接受数据
        while (true) {
            System.out.println("next command:");
            //调用ServiceScoket的accept方法，接收连接，并返回Scoket
            Socket s = ss.accept();
            //获取客户端发送过来的信息
            InputStream is = s.getInputStream();
            //翻译用户发送的信息，并打印在控制台
            byte[] bytes = new byte[1024];
            int len = 0;
            String cl_str = new String();
            while ((len = is.read(bytes)) != -1) {
                cl_str = new String(bytes, 0, len);
                System.out.println(cl_str);
            }
            //处理得到的命令str
            String answer = new String();
            String[] prem = cl_str.split(" ");
            switch (prem[0]) {
                case "create":
                    if (state.compareTo("no user") == 0){
                        answer = "not login";
                    } else if(state.compareTo("user") == 0 || state.compareTo("admin") == 0) {
                        user_info user1 = new user_info();
                        user1.name = prem[1];
                        user1.psd = prem[2];
                        if(user1.add_user() == 1){
                            answer = "add user success";
                        } else {
                            answer = "user already exits";
                        }
                    } else if(state.compareTo("root") == 0) {
                        admin_info admin1 = new admin_info();
                        admin1.name = prem[1];
                        admin1.psd = prem[2];
                        if(admin1.add_admin() == 1){
                            answer = "add admin success";
                        } else {
                            answer = "admin already exits";
                        }
                    }
                    break;
                case "login":
                    if (prem[1].compareTo("root") == 0 && prem[2].compareTo("root") == 0){
                        answer = "root login success";
                        state = "root";
                    } else {
                        admin_info admin1 = new admin_info();
                        admin1.name = prem[1];
                        admin1.psd = prem[2];
                        if (admin1.check_admin() == 1) {
                            answer = "admin login success";
                            state = "admin";
                        } else {
                            user_info user1 = new user_info();
                            user1.name = prem[1];
                            user1.psd = prem[2];
                            if(user1.check_user() == 1){
                                user_name = user1.name;
                                answer = "uesr login success";
                            } else {
                                answer = "login fail";
                            }
                        }
                    }
                    break;
                case "logout":
                    user_name = " ";
                    state = "no user";
                    answer = "logout success";
                    break;
                case "delete":
                    if (state.compareTo("root") == 0){
                        admin_info admin1 = new admin_info();
                        admin1.name = prem[1];
                        admin1.psd = prem[2];
                        admin1.rm_admin();
                        answer = "delete admin over";
                    } else {
                        answer = "only root could delete admin";
                    }
                    break;
                case "addroom":
                    if (state.compareTo("admin") == 0) {
                       home_info home1 = new home_info();
                       home1.room_number = prem[1];
                       if (home1.add_home() == 1) {
                           answer = "add home ok";
                       } else {
                           answer = "add home fail";
                       }
                    } else {
                        answer = "only admin could add home";
                    }
                    break;
                case "reserve_room":
                    if (state.compareTo("user") == 0) {
                        reservation_info  res1 = new reservation_info();
                        res1.reserve_room = prem[1];
                        res1.people_number = prem[2];
                        res1.start_date = prem[3];
                        res1.end_date = prem[4];
                        res1.user_num = user_name;
                        int order_num = res1.add_reservation();
                        if (order_num != 0) {
                            answer = "订单号"+order_num+"预定旅客名"+res1.user_num+"预定人数"+res1.people_number+"预定入住日期"+res1.start_date+"(年月日)  预定退房日期"+res1.end_date+" 预定房间号"+res1.reserve_room;
                        } else {
                            answer = "reserve room failed";
                        }
                    } else {
                        answer = "only user could reserve room";
                    }
                    break;
                case "show_reservations":
                    if (state.compareTo("admin") == 0) {
                        reservation_info  res1 = new reservation_info();
                        ArrayList<String> allinfo = res1.get_info_admin();
                        answer = "";
                        for (int i = 0; i < allinfo.size(); i++) {
                            answer = answer + allinfo.get(i).toString()+ "\n";
                        }
                    } else {
                        answer = "not admin no auth";
                    }
                    break;
                case "show_reservation":
                    if (state.compareTo("user") == 0) {
                        reservation_info  res1 = new reservation_info();
                        res1.user_num = user_name;
                        ArrayList<String> allinfo = res1.get_info_admin();
                        answer = "";
                        for (int i = 0; i < allinfo.size(); i++) {
                            answer = answer + allinfo.get(i).toString()+ "\n";
                        }
                    } else {
                        answer = "not user no auth";
                    }
                    break;
                default:
                    break;
            }
            //下面服务器回写给客户端的操作
            System.out.println("当前状态:"+ state + "\t user name:" + user_name);
            //调用Scoket的getOupputStream()方法，返回此服务器的输出流
            OutputStream os = s.getOutputStream();

            String strClient = answer;
            //将要回写的信息翻译成字节流并回写
            os.write(strClient.getBytes());

            //关闭资源
            os.close();
            is.close();

        }

    }
}
