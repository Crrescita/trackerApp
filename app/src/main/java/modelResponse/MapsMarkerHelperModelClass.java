package modelResponse;

public class MapsMarkerHelperModelClass {

    private String rowI_id,case_check_id,check_id,name,address,landmark,candidate_gps_address, appointment, vendor_request_id, source_id,
            clientID, process_id, invoiceDetailsOne, invoiceDetailsTwo, invoiceDetailsThree, familyId, minLimitPhoto, maxLimitPhoto, km_obtain_from_way_point;
    private int markerImagePath;

    public MapsMarkerHelperModelClass() {
    }

    public MapsMarkerHelperModelClass(String rowI_id, String case_check_id, String check_id,
                                      String name, String address, String landmark, String candidate_gps_address,
                                      String appointment,
                                      String vendor_request_id,
                                      String source_id,
                                      String clientID,
                                      String process_id,
                                      String invoiceDetailsOne,
                                      String invoiceDetailsTwo,
                                      String invoiceDetailsThree,
                                      String familyId,
                                      String minLimitPhoto,
                                      String maxLimitPhoto,
                                      String km_obtain_from_way_point,
                                      int markerImagePath) {
        this.rowI_id = rowI_id;
        this.case_check_id = case_check_id;
        this.check_id = check_id;
        this.name = name;
        this.address = address;
        this.landmark = landmark;
        this.candidate_gps_address = candidate_gps_address;

        this.appointment = appointment;
        this.vendor_request_id = vendor_request_id;
        this.source_id = source_id;
        this.clientID = clientID;
        this.process_id = process_id;
        this.invoiceDetailsOne = invoiceDetailsOne;
        this.invoiceDetailsTwo = invoiceDetailsTwo;
        this.invoiceDetailsThree = invoiceDetailsThree;
        this.familyId = familyId;
        this.minLimitPhoto = minLimitPhoto;
        this.maxLimitPhoto = maxLimitPhoto;
        this.km_obtain_from_way_point = km_obtain_from_way_point;
        this.markerImagePath = markerImagePath;
    }

    public int getMarkerImagePath() {
        return markerImagePath;
    }

    public void setMarkerImagePath(int markerImagePath) {
        this.markerImagePath = markerImagePath;
    }

    public String getRowI_id() {
        return rowI_id;
    }

    public void setRowI_id(String rowI_id) {
        this.rowI_id = rowI_id;
    }

    public String getCase_check_id() {
        return case_check_id;
    }

    public void setCase_check_id(String case_check_id) {
        this.case_check_id = case_check_id;
    }

    public String getCheck_id() {
        return check_id;
    }

    public void setCheck_id(String check_id) {
        this.check_id = check_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCandidate_gps_address() {
        return candidate_gps_address;
    }

    public void setCandidate_gps_address(String candidate_gps_address) {
        this.candidate_gps_address = candidate_gps_address;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getVendor_request_id() {
        return vendor_request_id;
    }

    public void setVendor_request_id(String vendor_request_id) {
        this.vendor_request_id = vendor_request_id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getProcess_id() {
        return process_id;
    }

    public void setProcess_id(String process_id) {
        this.process_id = process_id;
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

    public void setInvoiceDetailsThree(String invoiceDetailsThree) {
        this.invoiceDetailsThree = invoiceDetailsThree;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getMinLimitPhoto() {
        return minLimitPhoto;
    }

    public void setMinLimitPhoto(String minLimitPhoto) {
        this.minLimitPhoto = minLimitPhoto;
    }

    public String getMaxLimitPhoto() {
        return maxLimitPhoto;
    }

    public void setMaxLimitPhoto(String maxLimitPhoto) {
        this.maxLimitPhoto = maxLimitPhoto;
    }

    public String getKm_obtain_from_way_point() {
        return km_obtain_from_way_point;
    }

    public void setKm_obtain_from_way_point(String km_obtain_from_way_point) {
        this.km_obtain_from_way_point = km_obtain_from_way_point;
    }
}
