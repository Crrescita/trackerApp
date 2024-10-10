
package modelResponse.feassigned_check_response;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("vendor_sent_date")
    @Expose
    private String vendorSentDate;
    @SerializedName("vendor_request_id")
    @Expose
    private String vendorRequestId;
    @SerializedName("case_check_id")
    @Expose
    private String caseCheckId;
    @SerializedName("vendor_request_due_date")
    @Expose
    private String vendorRequestDueDate;

    @SerializedName("vendor_request_due_date_for_sort")
    @Expose
    private String vendorRequestDueDateForSort;

    @SerializedName("is_auto_closure")
    @Expose
    private String is_auto_closure;

    @SerializedName("is_candidate_signature_required")
    @Expose
    private String is_candidate_signature_required;

    @SerializedName("candidate_photo")
    @Expose
    private String candidate_photo;

    @SerializedName("nid_photo")
    @Expose
    private String nid_photo;

    @SerializedName("house_photo")
    @Expose
    private String house_photo;

    @SerializedName("address_proof")
    @Expose
    private String address_proof;

    @SerializedName("landmark_photo")
    @Expose
    private String landmark_photo;



    @SerializedName("fe_id")
    @Expose
    private String feId;
    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("check_type")
    @Expose
    private String checkType;
    @SerializedName("process_id")
    @Expose
    private String processId;
    @SerializedName("check_id")
    @Expose
    private String checkId;
    @SerializedName("check_name")
    @Expose
    private String checkName;
    @SerializedName("client_Name")
    @Expose
    private String clientName;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("case_priority_flag")
    @Expose
    private String casePriorityFlag;
    @SerializedName("appointment")
    @Expose
    private String appointment;
    @SerializedName("application_number")
    @Expose
    private String applicationNumber;
    @SerializedName("FasTag_Required")
    @Expose
    private String fastagRequired;
    @SerializedName("Appointment_Slot")
    @Expose
    private String appointmentSlot;
    @SerializedName("Appointment_Area")
    @Expose
    private String appointmentArea;
    @SerializedName("re_work_flag")
    @Expose
    private String reWorkFlag;
    @SerializedName("re_work_comment")
    @Expose
    private String reWorkComment;
    @SerializedName("check_gps")
    @Expose
    private String checkGps;
    @SerializedName("candidate_Name")
    @Expose
    private String candidateName;
    @SerializedName("candidate_Father_Name")
    @Expose
    private String candidateFatherName;
    @SerializedName("candidate_number")
    @Expose
    private String candidateNumber;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("family_id")
    @Expose
    private String family_id = "";

    @SerializedName("documents_min_limit")
    @Expose
    private String documents_min_limit="2";

    @SerializedName("documents_max_limit")
    @Expose
    private String documents_max_limit="20";

    //ArrayList<EducationPhotoArray> ravi = new ArrayList<EducationPhotoArray>();


    public String getVendorRequestDueDateForSort() {
        return vendorRequestDueDateForSort;
    }

    public void setVendorRequestDueDateForSort(String vendorRequestDueDateForSort) {
        this.vendorRequestDueDateForSort = vendorRequestDueDateForSort;
    }

    @SerializedName("documents")
    @Expose
    private List<EducationPhotoArray> educationPhotoArrays=null;


    @SerializedName("stated_check_fields")
    @Expose
    private List<StatedCheckField> statedCheckFields = null;
    @SerializedName("check_fields")
    @Expose
    private List<CheckField> checkFields = null;
    @SerializedName("check_special_instruction")
    @Expose
    private String checkSpecialInstruction;

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    @SerializedName("invoice_details_one")
    @Expose
    private String invoiceDetailsOne;

    @SerializedName("invoice_details_two")
    @Expose
    private String invoiceDetailsTwo;

    @SerializedName("invoice_details_three")
    @Expose
    private String invoiceDetailsThree;


    @SerializedName("candidate_gps_address")
    @Expose
    private String candidateGpsAddress;


    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    protected Datum(Parcel in) {
        this.vendorSentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorRequestId = ((String) in.readValue((String.class.getClassLoader())));
        this.caseCheckId = ((String) in.readValue((String.class.getClassLoader())));
        this.vendorRequestDueDate = ((String) in.readValue((String.class.getClassLoader())));
        this.feId = ((String) in.readValue((String.class.getClassLoader())));
        this.sourceId = ((String) in.readValue((String.class.getClassLoader())));
        this.checkType = ((String) in.readValue((String.class.getClassLoader())));
        this.processId = ((String) in.readValue((String.class.getClassLoader())));
        this.checkId = ((String) in.readValue((String.class.getClassLoader())));
        this.checkName = ((String) in.readValue((String.class.getClassLoader())));
        this.clientName = ((String) in.readValue((String.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
        this.casePriorityFlag = ((String) in.readValue((String.class.getClassLoader())));
        this.appointment = ((String) in.readValue((String.class.getClassLoader())));
        this.applicationNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.fastagRequired = ((String) in.readValue((String.class.getClassLoader())));
        this.appointmentSlot = ((String) in.readValue((String.class.getClassLoader())));
        this.appointmentArea = ((String) in.readValue((String.class.getClassLoader())));
        this.reWorkFlag = ((String) in.readValue((String.class.getClassLoader())));
        this.reWorkComment = ((String) in.readValue((String.class.getClassLoader())));
        this.checkGps = ((String) in.readValue((String.class.getClassLoader())));
        this.candidateName = ((String) in.readValue((String.class.getClassLoader())));
        this.candidateFatherName = ((String) in.readValue((String.class.getClassLoader())));
        this.candidateNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.pinCode = ((String) in.readValue((String.class.getClassLoader())));

        this.family_id = ((String) in.readValue((String.class.getClassLoader())));
        this.documents_min_limit = ((String) in.readValue((String.class.getClassLoader())));
        this.documents_max_limit = ((String) in.readValue((String.class.getClassLoader())));

        this.address = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.statedCheckFields, (StatedCheckField.class.getClassLoader()));
        in.readList(this.educationPhotoArrays, (StatedCheckField.class.getClassLoader()));
        in.readList(this.checkFields, (CheckField.class.getClassLoader()));
        this.checkSpecialInstruction = ((String) in.readValue((String.class.getClassLoader())));
        this.invoiceDetailsOne = ((String) in.readValue((String.class.getClassLoader())));
        this.invoiceDetailsTwo = ((String) in.readValue((String.class.getClassLoader())));
        this.invoiceDetailsThree = ((String) in.readValue((String.class.getClassLoader())));
        this.candidateGpsAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.is_auto_closure = ((String) in.readValue((String.class.getClassLoader())));

        this.candidate_photo = ((String) in.readValue((String.class.getClassLoader())));
        this.nid_photo = ((String) in.readValue((String.class.getClassLoader())));
        this.house_photo = ((String) in.readValue((String.class.getClassLoader())));
        this.address_proof = ((String) in.readValue((String.class.getClassLoader())));
        this.landmark_photo = ((String) in.readValue((String.class.getClassLoader())));
        this.is_candidate_signature_required = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Datum() {
    }


    public String getInvoiceDetailsOne() {
        return invoiceDetailsOne;
    }

    public void setInvoiceDetailsOne(String invoiceDetailsOne) {
        this.invoiceDetailsOne = invoiceDetailsOne;
    }

    public String getInvoiceDetailsTwo() {
        return invoiceDetailsTwo;
    }

    public void setInvoiceDetailsTwo(String invoiceDetailsTwo) {
        this.invoiceDetailsTwo = invoiceDetailsTwo;
    }

    public String getInvoiceDetailsThree() {
        return invoiceDetailsThree;
    }

    public String getCandidateGpsAddress() {
        return candidateGpsAddress;
    }

    public void setCandidateGpsAddress(String candidateGpsAddress) {
        this.candidateGpsAddress = candidateGpsAddress;
    }

    public void setInvoiceDetailsThree(String invoiceDetailsThree) {
        this.invoiceDetailsThree = invoiceDetailsThree;
    }

    public String getVendorSentDate() {
        return vendorSentDate;
    }

    public void setVendorSentDate(String vendorSentDate) {
        this.vendorSentDate = vendorSentDate;
    }

    public String getVendorRequestId() {
        return vendorRequestId;
    }

    public void setVendorRequestId(String vendorRequestId) {
        this.vendorRequestId = vendorRequestId;
    }

    public String getCaseCheckId() {
        return caseCheckId;
    }

    public void setCaseCheckId(String caseCheckId) {
        this.caseCheckId = caseCheckId;
    }

    public String getVendorRequestDueDate() {
        return vendorRequestDueDate;
    }

    public void setVendorRequestDueDate(String vendorRequestDueDate) {
        this.vendorRequestDueDate = vendorRequestDueDate;
    }

    public String getFeId() {
        return feId;
    }

    public void setFeId(String feId) {
        this.feId = feId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCasePriorityFlag() {
        return casePriorityFlag;
    }

    public void setCasePriorityFlag(String casePriorityFlag) {
        this.casePriorityFlag = casePriorityFlag;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getFastagRequired() {
        return fastagRequired;
    }

    public void setFastagRequired(String fastagRequired) {
        this.fastagRequired = fastagRequired;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(String appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public String getAppointmentArea() {
        return appointmentArea;
    }

    public void setAppointmentArea(String appointmentArea) {
        this.appointmentArea = appointmentArea;
    }

    public String getReWorkFlag() {
        return reWorkFlag;
    }

    public void setReWorkFlag(String reWorkFlag) {
        this.reWorkFlag = reWorkFlag;
    }

    public String getReWorkComment() {
        return reWorkComment;
    }

    public void setReWorkComment(String reWorkComment) {
        this.reWorkComment = reWorkComment;
    }

    public String getCheckGps() {
        return checkGps;
    }

    public void setCheckGps(String checkGps) {
        this.checkGps = checkGps;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateFatherName() {
        return candidateFatherName;
    }

    public void setCandidateFatherName(String candidateFatherName) {
        this.candidateFatherName = candidateFatherName;
    }

    public String getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(String candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<StatedCheckField> getStatedCheckFields() {
        return statedCheckFields;
    }

    public void setStatedCheckFields(List<StatedCheckField> statedCheckFields) {
        this.statedCheckFields = statedCheckFields;
    }


    public String getDocuments_min_limit() {
        return documents_min_limit;
    }

    public void setDocuments_min_limit(String documents_min_limit) {
        this.documents_min_limit = documents_min_limit;
    }

    public String getDocuments_max_limit() {
        return documents_max_limit;
    }

    public void setDocuments_max_limit(String documents_max_limit) {
        this.documents_max_limit = documents_max_limit;
    }

    public List<EducationPhotoArray> getEducationPhotoArrays() {
        return educationPhotoArrays;
    }

    public void setEducationPhotoArrays(List<EducationPhotoArray> educationPhotoArrays) {
        this.educationPhotoArrays = educationPhotoArrays;
    }

    public String getIs_auto_closure() {
        return is_auto_closure;
    }

    public void setIs_auto_closure(String is_auto_closure) {
        this.is_auto_closure = is_auto_closure;
    }

    public List<CheckField> getCheckFields() {
        return checkFields;
    }

    public void setCheckFields(List<CheckField> checkFields) {
        this.checkFields = checkFields;
    }

    public String getCheckSpecialInstruction() {
        return checkSpecialInstruction;
    }

    public void setCheckSpecialInstruction(String checkSpecialInstruction) {
        this.checkSpecialInstruction = checkSpecialInstruction;
    }

    public String getCandidate_photo() {
        return candidate_photo;
    }

    public void setCandidate_photo(String candidate_photo) {
        this.candidate_photo = candidate_photo;
    }

    public String getNid_photo() {
        return nid_photo;
    }

    public void setNid_photo(String nid_photo) {
        this.nid_photo = nid_photo;
    }

    public String getHouse_photo() {
        return house_photo;
    }

    public void setHouse_photo(String house_photo) {
        this.house_photo = house_photo;
    }

    public String getAddress_proof() {
        return address_proof;
    }

    public void setAddress_proof(String address_proof) {
        this.address_proof = address_proof;
    }

    public String getLandmark_photo() {
        return landmark_photo;
    }

    public void setLandmark_photo(String landmark_photo) {
        this.landmark_photo = landmark_photo;
    }

    public String getIs_candidate_signature_required() {
        return is_candidate_signature_required;
    }

    public void setIs_candidate_signature_required(String is_candidate_signature_required) {
        this.is_candidate_signature_required = is_candidate_signature_required;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vendorSentDate);
        dest.writeValue(vendorRequestId);
        dest.writeValue(caseCheckId);
        dest.writeValue(vendorRequestDueDate);
        dest.writeValue(feId);
        dest.writeValue(sourceId);
        dest.writeValue(checkType);
        dest.writeValue(processId);
        dest.writeValue(checkId);
        dest.writeValue(checkName);
        dest.writeValue(clientName);
        dest.writeValue(clientId);
        dest.writeValue(casePriorityFlag);
        dest.writeValue(appointment);
        dest.writeValue(applicationNumber);
        dest.writeValue(fastagRequired);
        dest.writeValue(appointmentSlot);
        dest.writeValue(appointmentArea);
        dest.writeValue(reWorkFlag);
        dest.writeValue(reWorkComment);
        dest.writeValue(checkGps);
        dest.writeValue(candidateName);
        dest.writeValue(candidateFatherName);
        dest.writeValue(candidateNumber);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(country);
        dest.writeValue(pinCode);

        dest.writeValue(family_id);
        dest.writeValue(documents_min_limit);
        dest.writeValue(documents_max_limit);

        dest.writeValue(address);
        dest.writeList(statedCheckFields);
        dest.writeList(educationPhotoArrays);
        dest.writeList(checkFields);
        dest.writeValue(checkSpecialInstruction);
        dest.writeValue(invoiceDetailsOne);
        dest.writeValue(invoiceDetailsTwo);
        dest.writeValue(invoiceDetailsThree);
        dest.writeValue(candidateGpsAddress);
        dest.writeValue(is_auto_closure);

        dest.writeValue(candidate_photo);
        dest.writeValue(nid_photo);
        dest.writeValue(house_photo);
        dest.writeValue(address_proof);
        dest.writeValue(landmark_photo);
        dest.writeValue(is_candidate_signature_required);
    }

    public int describeContents() {
        return  0;
    }

}
