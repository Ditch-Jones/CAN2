package com.example.joshi.can.Exception;

/**
 * Created by Joshi on 11.08.2017.
 */

public class OwnerIdMustBeLargerThanZeroException extends Throwable {
    public OwnerIdMustBeLargerThanZeroException (String str) {
        super(str);
    }
}
