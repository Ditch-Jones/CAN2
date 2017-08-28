package com.example.joshi.can.Exception;

/**
 * Created by Joshi on 11.08.2017.
 */

public class PicIdMustBeLargerThanZeroException extends Throwable {
    public PicIdMustBeLargerThanZeroException (String str) {
        super(str);
    }
}
