
package modelResponse.feassigned_check_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationPhotoArray implements Parcelable
{

    @SerializedName("DOCUMENT_NAME")
    @Expose
    private String documentName;
    @SerializedName("document_ID")
    @Expose
    private String documentId;
    @SerializedName("S3_PATH")
    @Expose
    private String imageUrl;

    public final static Creator<EducationPhotoArray> CREATOR = new Creator<EducationPhotoArray>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EducationPhotoArray createFromParcel(Parcel in) {
            return new EducationPhotoArray(in);
        }

        public EducationPhotoArray[] newArray(int size) {
            return (new EducationPhotoArray[size]);
        }

    }
    ;

    protected EducationPhotoArray(Parcel in) {
        this.documentName = ((String) in.readValue((String.class.getClassLoader())));
        this.documentId = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public EducationPhotoArray() {
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(documentName);
        dest.writeValue(documentId);
        dest.writeValue(imageUrl);
    }

    public int describeContents() {
        return  0;
    }

}
