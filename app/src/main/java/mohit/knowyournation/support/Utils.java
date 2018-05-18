package mohit.knowyournation.support;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by mohit on 2/12/17.
 */

public class Utils {
    public static ProgressDialog progressDialog;

    public static void startProgressBar(Context context, String msg) {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
        }


        try {
            progressDialog.setMessage(msg);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Loading Countries");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static void updateProgressbar(String msg){
        if(progressDialog!= null){
            Helper.log("Updated");
            progressDialog.setMessage(msg);
        }
    }

    public static void dismissProgressBar() {
        if(progressDialog != null){
            if(progressDialog.isShowing()){
                Helper.log("Dismiss progress bar");
                progressDialog.dismiss();
            }
        }
    }
}
