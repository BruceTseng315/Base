package com.kevin.Exception;


/**
 * Created by page on 2018/8/14.
 */
public class BaseException extends Exception {
    private Integer code;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BaseException withInfo(String info){
        this.info = info;
        return this;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BaseException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public static void main(String[] args) {
        String s = "9642 ,9643 ,9644 ,9645 ,9646 ,9647 ,9648 ,9649 ,9650 ,9651 ,9652 ,9653 ,9654 ,9655 ,9656 ,9657 ,9658 ,9659 ,9660 ,9661 ,9662 ,9663 ,9664 ,9665 ,9666 ,9667 ,9668 ,9669 ,9671 ,9672 ,9673 ,9674 ,9675 ,9676 ,9677 ,9678 ,9679 ,9680 ,9681 ,9682 ,9683 ,9684 ,9685 ,9686 ,9695 ,9696 ,9697 ,9700 ,9701 ,9702";
        String[] split = s.split(",");
        for (String s1:split){
            System.out.println("insert into USER_ROLE(account_id,role_id)VALUES("+s1+",4);");
        }
    }

}


