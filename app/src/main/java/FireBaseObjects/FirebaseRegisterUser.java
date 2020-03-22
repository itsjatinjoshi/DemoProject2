package FireBaseObjects;

public class FirebaseRegisterUser {
    String register_userId;
    int register_phonNo;
    String register_user_name, register_first_name, register_last_name, register_emailId, register_password,
            register_qualification, register_occupation;

    public FirebaseRegisterUser() {
    }

    public FirebaseRegisterUser(String register_userId, int register_phonNo, String register_user_name, String register_first_name, String register_last_name, String register_emailId, String register_password, String register_qualification, String register_occupation) {
        this.register_userId = register_userId;
        this.register_phonNo = register_phonNo;
        this.register_user_name = register_user_name;
        this.register_first_name = register_first_name;
        this.register_last_name = register_last_name;
        this.register_emailId = register_emailId;
        this.register_password = register_password;
        this.register_qualification = register_qualification;
        this.register_occupation = register_occupation;
    }

    public String getRegister_userId() {
        return register_userId;
    }

    public void setRegister_userId(String register_userId) {
        this.register_userId = register_userId;
    }

    public int getRegister_phonNo() {
        return register_phonNo;
    }

    public void setRegister_phonNo(int register_phonNo) {
        this.register_phonNo = register_phonNo;
    }

    public String getRegister_user_name() {
        return register_user_name;
    }

    public void setRegister_user_name(String register_user_name) {
        this.register_user_name = register_user_name;
    }

    public String getRegister_first_name() {
        return register_first_name;
    }

    public void setRegister_first_name(String register_first_name) {
        this.register_first_name = register_first_name;
    }

    public String getRegister_last_name() {
        return register_last_name;
    }

    public void setRegister_last_name(String register_last_name) {
        this.register_last_name = register_last_name;
    }

    public String getRegister_emailId() {
        return register_emailId;
    }

    public void setRegister_emailId(String register_emailId) {
        this.register_emailId = register_emailId;
    }

    public String getRegister_password() {
        return register_password;
    }

    public void setRegister_password(String register_password) {
        this.register_password = register_password;
    }

    public String getRegister_qualification() {
        return register_qualification;
    }

    public void setRegister_qualification(String register_qualification) {
        this.register_qualification = register_qualification;
    }

    public String getRegister_occupation() {
        return register_occupation;
    }

    public void setRegister_occupation(String register_occupation) {
        this.register_occupation = register_occupation;
    }
}
