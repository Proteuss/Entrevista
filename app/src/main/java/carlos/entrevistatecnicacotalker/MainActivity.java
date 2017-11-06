package carlos.entrevistatecnicacotalker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class    MainActivity extends AppCompatActivity {
    EditText input2;
    NumberPicker numberPicker;
    ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPicker=findViewById(R.id.input1);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(40);
        input2=findViewById(R.id.input2);
        listView1 = (ListView) findViewById(R.id.listview);



    }
    //Ejecuta el request al api al presionar el boton-1
    public void APIRequestButton(View view){
        APIRequest(numberPicker.getValue());
    }
    //Hace un request al api con el parametro length. Escribe el output como un String en el EditText input-2.
    public void APIRequest(int length){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://s8wojkby0k.execute-api.sa-east-1.amazonaws.com/prod/practica";
        JSONObject requestObject=new JSONObject();
        try{
            requestObject.put("length",length);
        }
        catch (JSONException e){

        }
        final String mRequestBody=requestObject.toString();
        StringRequest arrayRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONArray array=new JSONArray(response);
                            input2.setText(parseArray(array));
                        }catch(JSONException e){

                        }
                    }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        input2.setText(error.getMessage());
                    }}
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(arrayRequest);
    }

    //Lee el JSONArray del request y une los caracteres en un String
    public String parseArray(JSONArray array){
        String result="";
        for(int i=0;i<array.length();i++){
            try{
                result+=array.get(i);

            }catch (JSONException e){

            }
        }
        return result;
    }
    //Retorna una lista con todos los strings generados al reemplazar los * por 1 o 0
    public List<String> replaceAsterisk(String input){
        List<String> initList=new Vector();
        initList.clear();
        initList.add(input);
        return replaceAsteriskAux(initList,0,input.length());
    }
    //Funcion recursiva para el replaceAsterisk
    public List<String> replaceAsteriskAux(List<String> list, int pos,int size){
        List<String> auxList=new Vector();
        for(int i=pos;i<size;i++){
            if(list.get(0).charAt(i)=='*'){
                for(String string:list){
                    auxList.add(string.substring(0,i)+1+string.substring(i+1,size));
                    auxList.add(string.substring(0,i)+0+string.substring(i+1,size));


                }
                return replaceAsteriskAux(auxList,i+1,size);
            }
        }
        if(list.size()<2){
            list.clear();
        }
        return list;
    }

    //Se ejecuta cada vez que se clickea el boton-2
    //Genera la lista de combinaciones en un listview
    public void generateOutput(View view){
        if(input2.getText().toString().matches("[01\\*]*")){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, replaceAsterisk(input2.getText().toString()));
            listView1.setAdapter(adapter);
        }
        else{
            Toast.makeText(this,"Error, el texto debe contener solamente caracteres 0, 1 y *.",
                    Toast.LENGTH_LONG).show();
        }

    }
}
