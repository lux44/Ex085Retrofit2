package com.lux.ex085retrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;

import com.lux.ex085retrofit2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    //HTTP 통신 작업을 위한 Library
    //1. OkHttp - 처음 개발된 라이브러리 oracle 보유
    //2. Retrofit2 - OkHttp를 개량한 라이브러리 - sqareup 이 보유 [가장 많이 사용됨]
    //3. Volley - Google에서 인수 - 그래서 많은 기대를 받았으나 업데이트 지원정책에 혼선이 있음.

    //가장 많이 사용되는 Retrofit2 을 사용
    //라이브러리 추가

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1.setOnClickListener(view -> clickBtn1());
        binding.btn2.setOnClickListener(view -> clickBtn2());
        binding.btn3.setOnClickListener(view -> clickBtn3());
        binding.btn4.setOnClickListener(view -> clickBtn4());
        binding.btn5.setOnClickListener(view -> clickBtn5());
        binding.btn6.setOnClickListener(view -> clickBtn6());
        binding.btn7.setOnClickListener(view -> clickBtn7());
        binding.btn8.setOnClickListener(view -> clickBtn8());
        binding.btn9.setOnClickListener(view -> clickBtn9());
    }
    void clickBtn9(){
        //json array를 파싱하지 않고 그냥 문자열 String 으로 응답 받기
        //단, 결과를 String으로 받으려면 ScalarsConverter 가 필요함. - 라이브러리 추가
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://sens0104.dothome.co.kr/");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit=builder.build();

        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<String> call=retrofitService.getJsonArrayToString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s=response.body();
                binding.tv.setText(s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.tv.setText("error" +t.getMessage());
            }
        });
    }
    void clickBtn8(){
        //json array를 파싱하여 ArrayList<Member> 객체로 결과 받기
        Retrofit retrofit=RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<Member>> call=retrofitService.getJsonArray();
        call.enqueue(new Callback<ArrayList<Member>>() {
            @Override
            public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                ArrayList<Member> members=response.body();

                //실무에서는 이 리스트를 보여주기 위해 RecyclerView를 이용
                StringBuffer buffer=new StringBuffer();
                for (Member m:members){
                    buffer.append(m.no+","+m.name+","+m.age+","+m.address+","+m.favorite);
                }
                binding.tv.setText(buffer.toString());

            }

            @Override
            public void onFailure(Call<ArrayList<Member>> call, Throwable t) {
                binding.tv.setText("error" +t.getMessage());
            }
        });


    }
    void clickBtn7(){
        //post 방식으로 전달 - 값 하나씩
        String title="android";
        String message="Hello Android";

        Retrofit retrofit=RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<Item> call=retrofitService.postMethodTest2(title, message);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item=response.body();
                binding.tv.setText(item.title+"\n"+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error" +t.getMessage());
            }
        });
    }
    void clickBtn6(){
        //post 방식으로 Item 객체를 서버에 전달
        Item item=new Item("nice","Good afternoon");

        Retrofit retrofit=RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        //서버로 보낼 값을 가진 객체를 그냥 바로 보내기
        Call<Item> call = retrofitService.postMethodTest(item);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item1=response.body();
                binding.tv.setText(item1.title+"\n"+item1.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error" +t.getMessage());
            }
        });
    }
    void clickBtn5(){
        //get방식으로 값들을 한방에 서버에 전달 - Map Collection 사용

        //1. Retrofit 객체 만드는 작업 4줄이 매번 똑같음  RetrofitHelper
        Retrofit retrofit=RetrofitHelper.getRetrofitInstance();
        //2. 추상 메소드 설계 - getMethodTest3()
        //3. 인터페이스 객체 생성
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        //4.추상메소드 실행
        //보낼 데이터들을 Map Collection 으로 하나로 묶기
        Map<String, String> datas=new HashMap<>();
        datas.put("title","abcd"); //key, value
        datas.put("msg","have a good day"); //key, value

        Call<Item> call=retrofitService.getMethodTest3(datas);
        //5. 네트워크 작성 시작
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item=response.body();
                binding.tv.setText(item.title+"-"+item.msg) ;
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error" +t.getMessage());
            }
        });


    }
    void clickBtn4(){
        //Get 방식으로 값을 서버에 전달 + 파일명 설정

        String title="third";
        String message="nice to meet you";

        //1.
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://sens0104.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        //2. 추상메소드 설계
        //3.
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        //4.
        Call<Item> call=retrofitService.getMethodTest2("getTest.php",title, message);
        //5.
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item=response.body();
                binding.tv.setText(item.title+"\n"+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패"+t.getMessage());
            }
        });

    }
    void clickBtn3(){
        //get 방식으로 값을 서버에 전달

        //서버에 보낼 데이터들
        String title="안녕하세요";
        String message="반가워요 retrofit";

        //1. retrofit 객체 생성
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://sens0104.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        //2. 인터페이스 설계 및 추상메소드 - getMethodTest();
        //3. 인터페이스 객체 생성
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        //4. 추상메소드 실행 하면서 서버에 전달할 값 파라미터로 지정
        Call<Item> call=retrofitService.getMethodTest(title,message);

        //5.네트워크 작업 시작
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item=response.body();
                binding.tv.setText(item.title+":"+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : "+t.getMessage());   //에러메세지
            }
        });
    }
   void clickBtn2(){
        //서버의 Path를 동적으로 지정할 수 있는 기능을 통해 json 데이터 읽어오기

        //Retrofit의 5단계
        //1. Retrofit 객체 생성
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://sens0104.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        //2. 인터페이스 설계 및 추성 메소드 작성 - getBoardJsonByPath()

        //3. 인터페이스 객체 생성
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        //4. 추상메소드를 호출해서 실제 네트워크 기능을 가진 객체 리턴
        Call<Item> call=retrofitService.getBoardJsonByPath("04Retrofit","board.json");

        //5. 실제 네트워크 작업 시작
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                //응답된 json을 GSon을 이용하여 Item 객체로 자동 파싱한 결과 body 얻기
                Item item=response.body();
                binding.tv.setText(item.title+"\n"+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("네트워크 통신 실패" +t.getMessage());
            }
        });
    }
    void  clickBtn1(){
        //단순하게 get 방식으로 json 문서를 읽어오기

        //Retrofit2 라이브러리를 이용하여 서버에서 json 데이터를 읽어와서 Item 객체로 곧바로 파싱

        //1. Retrofit 객체 생성
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl("http://sens0104.dothome.co.kr");  //서버 기본주소 설정
        builder.addConverterFactory(GsonConverterFactory.create()); //Gson으로 json을 자동 파싱하는 녀석 설정 - 이 설정을 하면
        // 통신결과 json 문자열을 자동 파싱하여 객체로 바로 얻을 수 있음.
        Retrofit retrofit=builder.build();

        //2. Retrofit의 동작을 정의하는 인터페이스 설계
        // RetrofitService.java interface 설계 및 추상메소드 : getBoardJson()

        //3. 2단계에서 설계한 RetrofitService를 객체로 생성
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        //4. 위에서 만든 RetrofitService 객체의 추상메소드를 호출하여 실제 서버작업을 수행하는 기능을 가진 Call 이라는 객체 리턴받기
        Call<Item> call=retrofitService.getBoardJson();

        //5. 위 4단계로 리턴받은 Call 객체에게 네트워크 작업을 수행하도록 요청
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                //파라미터로 전달된 response 응답 객체가 결과 데이터를 가지고 있음.
                //json 을 자동으로 Gson 파싱하여 Item 객체로 결과를 받음.
                Item item=response.body();
                binding.tv.setText(item.title+","+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("failure");
            }
        });
    }

}