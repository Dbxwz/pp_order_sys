import javax.print.DocFlavor;
import java.util.ArrayList;

public class reservation_info{
    public int info_num = 0;
    public String reserve_room;
    public String people_number;
    public String start_date ;
    public String end_date;
    public String user_num;
    public String filepath = "data/reservation_info.csv";
    public String[] headers = new String[]{ "info_num", "reserve_room", "people_number", "start_date", "end_date", "user_num"};

    private int com_data(String data1, String data2){
        if (data1.compareTo(data2) > 0) {
            return 2;
        } else if (data1.compareTo(data2) == 0){
            return 0;
        } else {
            return 1;
        }
    }

    public int add_reservation(){
        int mymax = 0;
        int flag = 0;
        ArrayList<String> allinfo = pp_csv_sql.read_info("data/home_info.csv");
        for (int i=0;i < allinfo.size(); i++){
            String[] prem = allinfo.get(i).split(",");
            if (this.reserve_room.compareTo(prem[0]) == 0)
                flag = 1;
        }
        if (flag == 0) {
            return 0;
        }
        allinfo = pp_csv_sql.read_info(this.filepath);
        for (int i=0;i < allinfo.size(); i++){
            String[] prem = allinfo.get(i).split(",");
            if(mymax < Integer.parseInt(prem[0])){
                mymax = Integer.parseInt(prem[0]);
            }
            if (this.reserve_room.compareTo(prem[1]) == 0)
                if(com_data(this.start_date, prem[4]) == 2 || com_data(this.end_date, prem[3]) == 1){
                    this.info_num = mymax + 1;
                    pp_csv_sql.write_info(this.filepath, this.headers, new String[]{String.valueOf(this.info_num), this.reserve_room,this.people_number,this.start_date,this.end_date,this.user_num});
                    return this.info_num;
                }
        }
        return 0;
    }

    public ArrayList<String> get_info_admin(){
        return pp_csv_sql.read_info(this.filepath);
    }

    public ArrayList<String> get_info_user(){
        ArrayList<String> allinfo = pp_csv_sql.read_info(this.filepath);
        ArrayList<String> myinfo = new ArrayList<String>();
        for (int i=0;i < allinfo.size(); i++) {
            String[] prem = allinfo.get(i).split(",");
            if (this.user_num.compareTo(prem[5]) == 0){
                myinfo.add(allinfo.get(i));
            }
        }
        return myinfo;
    }
}
