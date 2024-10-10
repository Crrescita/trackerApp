package utils;

import modelResponse.ModelError;

public interface MasterDataCallbackListner {
    void onMasterDataDownloadCompleted(String fe_id,String user_id,String vendor_id);
    void onCheckFieldDownloadCompleted();
    void onNotificationDownloadCompleted(int count);
    void onError(ModelError error);

}
