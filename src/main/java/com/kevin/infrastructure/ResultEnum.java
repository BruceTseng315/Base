package com.kevin.infrastructure;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-08-10
 * Time: 9:39
 */
public enum ResultEnum {
    params_illegal(400,"请求参数非法"),
    auth_illegal(401,"没有访问权限"),
    repeat_share_access_request(400004,"Only pending  request can be resent, please reload current page!"),
    share_not_exist(400005,"Share not exist"),
    no_permission_view(400010,"User not exist or no permission ！"),
    share_self(400006,"Cannot share to yourself")

    ;
    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
