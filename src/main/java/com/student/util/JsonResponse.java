package com.student.util;

public class JsonResponse<T>
{
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    public static final int SUCCESS_PARTIAL = 3;
    private int suc;
    private T msg;//message
    private Object obj = null;
    private Object[] attach;

    public int getSuc()
    {
        return suc;
    }

    public void setSuc(int suc)
    {
        this.suc = suc;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object[] getAttach()
    {
        return attach;
    }

    public void setAttach(Object[] attach)
    {
        this.attach = attach;
    }
}
