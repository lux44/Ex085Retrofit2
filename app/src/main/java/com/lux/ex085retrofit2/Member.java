package com.lux.ex085retrofit2;

//json 객체 1개의 정보를 가진 클래스
public class Member {
    //json의 식별 글씨와 같은 이름의 멤버 변수를 선언
    int no;
    String name;
    int age;
    String address;
    Boolean favorite;

    public Member() {
    }

    public Member(int no, String name, int age, String address, Boolean favorite) {
        this.no = no;
        this.name = name;
        this.age = age;
        this.address = address;
        this.favorite = favorite;
    }
}
