package com.example.hotel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Host_page_main extends AppCompatActivity {
private Button btn_reg_hotel,btn_pwd;
private TextView tv_Host;
//private String UserID;
private EditText et_name,et_address,et_PHnum,et_Capacity,et_checkin,et_checkout,et_price,et_pwd;
static String hotelName;
static String hotelAddress ;
static String hotelNumber;
static int hotelCapacity; // int 형으로 형변환
static String hotelCheckIn ;
static String hotelCheckOut;
static String hotelPrice ;
static String hotelUserID;
static String hotelPwd;
private String userID=MainActivity.userID;
private String data;

    private Handler mHandler;
    Socket socket;
    PrintWriter sendWriter;
    private String ip = "192.168.43.235";
    private int port = 8000;
    @Override
    protected void onStop() {
        super.onStop();
        try {
            sendWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_page_main);

        btn_reg_hotel=(Button)findViewById(R.id.reg_hotel);
        btn_pwd=(Button)findViewById(R.id.PWD);
        //tv_Host=(TextView)findViewById(R.id.Host);
       // Intent intent = getIntent();
        //UserID= intent.getStringExtra("username2");

        //tv_Host.setText(Host);

        mHandler = new Handler();

        new Thread() {
            public void run() {
                byte[] buf = new byte[1024];
                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);//소켓생성
                    socket = new Socket(serverAddr, port);
                    sendWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")), true);
                    //chatView.setText("[##] 서버와 연결이 되었습니다......");
                    //chatView.setText("이름을 입력해 주세요:");
                    //sendWriter = new PrintWriter(socket.getOutputStream()); //데이터 전송
                    //BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));//데이터 수신
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    while(true){
                        //read = input.readLine(); //데이터 수신
                        input.read(buf,0,1024); //데이터 수신
                        //final String data=new String(buf,0,1024);
                        data=new String(buf,0,1024);
                        //chatView.setText(data);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } }}.start();

        btn_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad=new AlertDialog.Builder(Host_page_main.this);
                ad.setMessage(data);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sendWriter.println(hotelPwd);
                            sendWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                //ad.setMessage("등록에 성공하였습니다");
                ad.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();;
                    }
                });
                ad.show();
            }
        });
        btn_reg_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linear =(LinearLayout) View.inflate(Host_page_main.this,R.layout.activity_host_dialog,null);
                AlertDialog.Builder ad=new AlertDialog.Builder(Host_page_main.this);
                ad.setMessage(userID+" 님의 숙박정보 등록");
                ad.setView(linear);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          et_name=(EditText)linear.findViewById(R.id.H_name);
                        et_address=(EditText)linear.findViewById(R.id.H_address);
                        et_PHnum=(EditText)linear.findViewById(R.id.H_PHnum);
                      et_Capacity=(EditText)linear.findViewById(R.id.H_Capacity);
                      et_checkin=(EditText)linear.findViewById(R.id.H_checkin);
                       et_checkout=(EditText)linear.findViewById(R.id.H_checkout);
                        et_price=(EditText)linear.findViewById(R.id.H_price);
                        et_pwd=(EditText)linear.findViewById(R.id.H_pwd);
                        hotelName = et_name.getText().toString();
                         hotelAddress = et_address.getText().toString();
                        hotelNumber = et_PHnum.getText().toString();
                        hotelCapacity = Integer.parseInt(et_Capacity.getText().toString()); // int 형으로 형변환
                        hotelCheckIn = et_checkin.getText().toString();
                        hotelCheckOut = et_checkout.getText().toString();
                        hotelPrice = et_price.getText().toString();
                        hotelPwd=et_pwd.getText().toString();
                        hotelUserID=userID;


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { // 서버 보낼때 그냥 string으로 못하므로 json 오브젝트로 만들어서 서버로 보내야 함(포장)
                                try {
                                    JSONObject jsonObject = new JSONObject(response); // 회원가입 요청을 한다음 결과값을 json 오브젝트로 받음, 성공 여부 알기 위해 함
                                    boolean success = jsonObject.getBoolean("success"); // php에 success가 가는데 그걸 받아와서 판단함
                                    if(success)
                                    {
                                        Toast.makeText(getApplicationContext(), "등록되었습니다.",Toast.LENGTH_LONG).show();
//                                        Intent intent = new Intent(Host_page_main.this, MainActivity.class);
//                                        startActivity(intent); // 가입 하고 메인화면 보내주기
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "등록에실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        Room_informationRequest room_informationRequest = new Room_informationRequest(hotelName, hotelAddress, hotelNumber, hotelCapacity,hotelCheckIn,hotelCheckOut,hotelPrice,hotelUserID,hotelPwd, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Host_page_main.this);
                        queue.add(room_informationRequest); // Volley를 이용해서 서버로 요청함
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();;
                    }
                });
                ad.show();
            }
        });
    }
}