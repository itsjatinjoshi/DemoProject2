package BackgroundTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterAPI extends AsyncTask<String, Void, String> {

    Context context;

    public RegisterAPI(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://127.0.0.1/wampapp/register.php";
        String login_url = "http://127.0.0.1/wampapp/login.php";
        String method = params[0];
        if (method.equals("register")) {
            String username = params[1];
            String user_first_name = params[2];
            String user_last_name = params[3];
            String email = params[4];
            String password = params[5];
            int phone = Integer.parseInt(params[6]);
            String type = params[7];
            String qualification = params[8];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("register_user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("register_firstname", "UTF-8") + "=" + URLEncoder.encode(user_first_name, "UTF-8") + "&" +
                        URLEncoder.encode("register_lastname", "UTF-8") + "=" + URLEncoder.encode(user_last_name, "UTF-8") + "&" +
                        URLEncoder.encode("register_email_id", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("register_password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("register_phone_num", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(phone), "UTF-8") + "&" +
                        URLEncoder.encode("register_type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&" +
                        URLEncoder.encode("register_occupation", "UTF-8") + "=" + URLEncoder.encode(qualification, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
               return "Registration Success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else if(method.equals("login"))
//        {
//            String login_name = params[1];
//            String login_pass = params[2];
//            try {
//                URL url = new URL(login_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
//                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                String response = "";
//                String line = "";
//                while ((line = bufferedReader.readLine())!=null)
//                {
//                    response+= line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return response;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    }


}
