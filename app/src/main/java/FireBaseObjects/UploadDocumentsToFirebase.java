package FireBaseObjects;

public class UploadDocumentsToFirebase {

    String fileName;
    String firebaseUrl;
    String timestamp;

    public UploadDocumentsToFirebase() {
    }

    public UploadDocumentsToFirebase(String fileName, String firebaseUrl) {
        this.fileName = fileName;
        this.firebaseUrl = firebaseUrl;
    }

    public UploadDocumentsToFirebase(String fileName, String firebaseUrl, String timestamp) {
        this.fileName = fileName;
        this.firebaseUrl = firebaseUrl;
        this.timestamp = timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFirebaseUrl() {
        return firebaseUrl;
    }

    public void setFirebaseUrl(String firebaseUrl) {
        this.firebaseUrl = firebaseUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
