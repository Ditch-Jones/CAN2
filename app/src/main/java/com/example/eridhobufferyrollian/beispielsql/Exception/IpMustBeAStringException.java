package com.example.joshi.can.Exception;

/**
 * Created by Joshi on 11.08.2017.
 */

public class IpMustBeAStringException extends Throwable {
    public IpMustBeAStringException(String str) {
        super(str);
    }
}
