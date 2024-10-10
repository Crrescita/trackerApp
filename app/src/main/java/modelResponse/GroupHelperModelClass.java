package modelResponse;

public class GroupHelperModelClass {

    private String rowI_id,group_id,grouping_date,no_of_total_checks,optimized_date,online_optimized_flag,offline_opitimized_flag;

    public GroupHelperModelClass() {
    }

    public GroupHelperModelClass(String rowI_id, String group_id, String grouping_date, String no_of_total_checks, String optimized_date, String online_optimized_flag, String offline_opitimized_flag) {
        this.rowI_id = rowI_id;
        this.group_id = group_id;
        this.grouping_date = grouping_date;
        this.no_of_total_checks = no_of_total_checks;
        this.optimized_date = optimized_date;
        this.online_optimized_flag = online_optimized_flag;
        this.offline_opitimized_flag = offline_opitimized_flag;
    }

    public String getRowI_id() {
        return rowI_id;
    }

    public void setRowI_id(String rowI_id) {
        this.rowI_id = rowI_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGrouping_date() {
        return grouping_date;
    }

    public void setGrouping_date(String grouping_date) {
        this.grouping_date = grouping_date;
    }

    public String getNo_of_total_checks() {
        return no_of_total_checks;
    }

    public void setNo_of_total_checks(String no_of_total_checks) {
        this.no_of_total_checks = no_of_total_checks;
    }

    public String getOptimized_date() {
        return optimized_date;
    }

    public void setOptimized_date(String optimized_date) {
        this.optimized_date = optimized_date;
    }

    public String getIsOnlineOptimized() {
        return online_optimized_flag;
    }

    public void setOnline_optimized_flag(String online_optimized_flag) {
        this.online_optimized_flag = online_optimized_flag;
    }

    public String getIsOfflineOptimized() {
        return offline_opitimized_flag;
    }

    public void setOffline_opitimized_flag(String offline_opitimized_flag) {
        this.offline_opitimized_flag = offline_opitimized_flag;
    }
}
