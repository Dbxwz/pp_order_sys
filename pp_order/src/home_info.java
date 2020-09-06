import java.util.ArrayList;

public class home_info{
    public String room_number;
    public String room_type;
    public String filepath = "data/home_info.csv";
    public String[] headers = new String[]{"room_number", "room_type"};

    public int add_home(){
        ArrayList<String> allinfo = pp_csv_sql.read_info(this.filepath);
        for (int i=0;i < allinfo.size(); i++){
            String[] prem = allinfo.get(i).split(",");
            if (this.room_number.compareTo(prem[0]) == 0)
                return 0;
        }
        pp_csv_sql.write_info(this.filepath, this.headers, new String[]{this.room_number});
        return 1;
    }

    public void rm_home() {
        pp_csv_sql.rm_info(this.filepath, this.headers, this.room_number);
    }
}
