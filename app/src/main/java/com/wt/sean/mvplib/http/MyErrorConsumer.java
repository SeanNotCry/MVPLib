package com.wt.sean.mvplib.http;

import io.reactivex.functions.Consumer;

public  abstract class MyErrorConsumer implements Consumer<Throwable> {


    @Override
    public void accept(Throwable throwable) throws Exception {
        if(throwable instanceof  ApiExcepction){
            doWhileOurExcepction(((ApiExcepction) throwable).getDisplayMessage());
        }else{
            doWhileOurExcepction("加载失败!");
        }
    }

     public abstract void doWhileOurExcepction(String displayMessage);
}
