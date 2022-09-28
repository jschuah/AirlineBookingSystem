package exception;

import org.omg.CORBA.DynAnyPackage.Invalid;

public class InvalidPlaceException extends Exception {

    public InvalidPlaceException(String s) {
        super(s);
    }

}
