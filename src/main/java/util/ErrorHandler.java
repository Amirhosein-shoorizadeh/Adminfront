package util;

import org.json.JSONObject;

public class ErrorHandler {
    public static String parseErrorMessage(String jsonBody) {
        try {
            JSONObject obj = new JSONObject(jsonBody);
            return obj.optString("Error", "خطای نامشخص");
        } catch (Exception e) {
            return "خطای نامشخص";
        }
    }
}
