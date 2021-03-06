package manager.app.com.hotels.uiView;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import manager.app.com.hotels.api.ApiClient;
import manager.app.com.hotels.model.Rooms;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView mainView;
    public static final String TAG="MainPresenter";

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }
    void getdata(){

      ApiClient.getService().getRooms().enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            mainView.hideLoading();
              try {
               //   Log.d(TAG, response.body().string());
                  JSONArray jsonArray = new JSONArray(response.body().string());
                  List<Rooms> roomsList=new ArrayList<>();

                  for (int i=0;i<jsonArray.length();i++){
                      JSONObject jsonObject = jsonArray.getJSONObject(i);

                          String room_number= jsonObject.getString("room_number");
                          String price =jsonObject.getString("price");
                          String  detail =jsonObject.getString("detail");
                          String image =  "http://192.168.86.103:3000/"+jsonObject.getString("image");
                          Rooms rooms = new Rooms(room_number,price,detail,image);
                          roomsList.add(rooms);

                      Log.d("c","hotels "+ image+"");
                  }
                 if (response.isSuccessful()&& response.body()!=null){
                     mainView.OnGetResult(roomsList);
                    // mainView.showLoading();
                 }
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
      });
    }
}
