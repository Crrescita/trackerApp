package adapter.modelAdapter;

/**
 * Created by Kapil Rathee on 20/9/18.
 */

public class AssignGroupListRecycleModel {

    private String group_id,group_Count,groupDate;

    public AssignGroupListRecycleModel() {
    }

    public AssignGroupListRecycleModel(String group_id,String group_Count,String groupDate) {
        this.group_id = group_id;
        this.group_Count = group_Count;
        this.groupDate = groupDate;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_Count() {
        return group_Count;
    }

    public void setGroup_Count(String group_Count) {
        this.group_Count = group_Count;
    }

    public String getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(String groupDate) {
        this.groupDate = groupDate;
    }
}
