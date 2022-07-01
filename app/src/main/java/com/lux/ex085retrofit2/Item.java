package com.lux.ex085retrofit2;

public class Item {
    //이 멤버변수 이름은 json 의 식별자명과 완전히 일치해야 함.
    String title;
    String msg;

    public Item() {
    }

    public Item(String title, String msg) {
        this.title = title;
        this.msg = msg;
    }
}
