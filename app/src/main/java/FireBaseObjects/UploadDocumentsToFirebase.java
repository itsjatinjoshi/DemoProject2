package FireBaseObjects;

public class UploadDocumentsToFirebase {

    String fileName;
    String firebaseUrl;

    public UploadDocumentsToFirebase() {
    }

    public UploadDocumentsToFirebase(String fileName, String firebaseUrl) {
        this.fileName = fileName;
        this.firebaseUrl = firebaseUrl;
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
}
