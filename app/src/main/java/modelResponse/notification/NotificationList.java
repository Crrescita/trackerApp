
package modelResponse.notification;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    private int isViewed = 0;
    private int rowId;

    public NotificationList(String id, String title, String details, String endDate, int isViewed, int rowId) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.endDate = endDate;
        this.isViewed = isViewed;
        this.rowId = rowId;
    }

    public NotificationList(String id, String title, String details, String endDate, int isViewed) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.endDate = endDate;
        this.isViewed = isViewed;
    }

    protected NotificationList(Parcel in) {
        id = in.readString();
        title = in.readString();
        details = in.readString();
        endDate = in.readString();
        isViewed = in.readInt();
        rowId = in.readInt();
    }

    public static final Creator<NotificationList> CREATOR = new Creator<NotificationList>() {
        @Override
        public NotificationList createFromParcel(Parcel in) {
            return new NotificationList(in);
        }

        @Override
        public NotificationList[] newArray(int size) {
            return new NotificationList[size];
        }
    };

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int isViewed() {
        return isViewed;
    }

    public void setViewed(int viewed) {
        isViewed = viewed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(details);
        parcel.writeString(endDate);
        parcel.writeInt(isViewed);
        parcel.writeInt(rowId);
    }
}
