import java.util.ArrayList;

public class admin_info{
    public String name;
    public String psd;
    public String filepath = "data/admin_info.csv";
    public String[] headers = new String[]{ "name", "psd"};

    public int check_admin(){
        ArrayList<String> allinfo = pp_csv_sql.read_info(this.filepath);
        for (int i=0; i < allinfo.size(); i++){
            String[] prem = allinfo.get(i).split(",");
            if (this.name.compareTo(prem[0]) == 0 && this.psd.compareTo(prem[1]) == 0)
                return 1;
        }
        return 0;
    }

    public int add_admin(){
        ArrayList<String> allinfo = pp_csv_sql.read_info(this.filepath);
        for (int i=0;i < allinfo.size(); i++){
            String[] prem = allinfo.get(i).split(",");
            if (this.name.compareTo(prem[0]) == 0)
                return 0;
        }
        pp_csv_sql.write_info(this.filepath, this.headers, new String[]{this.name,this.psd});
        return 1;
    }

    public void rm_admin(){
        pp_csv_sql.rm_info(this.filepath, this.headers, this.name+','+this.psd);
    }
}
