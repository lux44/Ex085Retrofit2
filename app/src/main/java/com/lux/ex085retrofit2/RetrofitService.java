package com.lux.ex085retrofit2;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {

    //1. 단순하게 Get 방식으로 json 문자열을 읽어오는 기능 추상 메소드
    @GET("/04Retrofit/board.json")  //접속하는 Method 와 파일의 경로 지정
    Call<Item> getBoardJson();

    //2. 경로의 이름은 위 1처럼 고정하지 않고 파라미터로 전달 받아서 지정할 수 있음. = [@Path]
    @GET("/{aaa}/{bbb}")
    Call<Item> getBoardJsonByPath(@Path("aaa") String path, @Path("bbb") String fileName);

    //3. get 방식으로 값을 서버에 전달(@Query)
    @GET("/04Retrofit/getTest.php")
    Call<Item> getMethodTest(@Query("title") String title, @Query("msg") String message);

    //4. get 방식으로 값을 서버에 전달(@Query) + 경로설정
    @GET("/04Retrofit/{aaa}")
    Call<Item> getMethodTest2(@Path("aaa") String fileName, @Query("title") String title, @Query("msg") String message);

    //5.Get 방식으로 데이터를 보낼때 한방에 보내는 방법 (Map 방식의 Collection 사용)
    @GET("/04Retrofit/getTest.php")
    Call<Item> getMethodTest3(@QueryMap Map<String, String> datas);

    //6. post 방식으로 데이터를 전달. - Item 객체를 통으로 보내기 [@Body] - 객체를 전달하면 자동으로 객체를 json 문자열로 변환해서
    // 서버로 전송함.
    @POST("04Retrofit/postTest.php")
    Call<Item> postMethodTest(@Body Item item);

    //7. POST 방식을 GET 방식의 @Query 처럼 하나씩 보내는 방법
    //@Field - 단, @Field 를 사용하려면 반드시 추가로 @FormUrlEncoded 가 필요함.
    @FormUrlEncoded
    @POST("04Retrofit/postTest2.php")
    Call<Item> postMethodTest2(@Field("title") String title, @Field("msg") String message);

    //8. json array받기
    @GET("04Retrofit/members.json")
    Call<ArrayList<Member>> getJsonArray();

    //9.서버의 응답결과를 단순 String 으로 받기(즉, Gson 파싱하지 않기)
    @GET("04Retrofit/members.json")
    Call<String> getJsonArrayToString();
}
