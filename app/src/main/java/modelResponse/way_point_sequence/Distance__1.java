
package modelResponse.way_point_sequence;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distance__1 implements Parcelable
{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private Integer value;
    public final static Creator<Distance__1> CREATOR = new Creator<Distance__1>() {


        public Distance__1 createFromParcel(android.os.Parcel in) {
            return new Distance__1(in);
        }

        public Distance__1 [] newArray(int size) {
            return (new Distance__1[size]);
        }

    }
    ;

    @SuppressWarnings({
        "unchecked"
    })
    protected Distance__1(android.os.Parcel in) {
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Distance__1() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
