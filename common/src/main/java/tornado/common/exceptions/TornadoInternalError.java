package tornado.common.exceptions;

import java.util.ArrayList;

public class TornadoInternalError extends Error {

    private static final long serialVersionUID = 6639694094043791236L;

    private final ArrayList<String> context = new ArrayList<>();

    public static RuntimeException unimplemented() {
        throw new TornadoInternalError("unimplemented");
    }

    public static RuntimeException unimplemented(String msg) {
        throw new TornadoInternalError("unimplemented: %s", msg);
    }

    public static RuntimeException unimplemented(String msg, Object... args) {
        throw new TornadoInternalError("unimplemented: " + msg, args);
    }

    public static RuntimeException shouldNotReachHere() {
        throw new TornadoInternalError("should not reach here");
    }

    public static RuntimeException shouldNotReachHere(String msg) {
        throw new TornadoInternalError("should not reach here: %s", msg);
    }

    public static RuntimeException shouldNotReachHere(String msg, Object... args) {
        throw new TornadoInternalError("should not reach here: " + msg, args);
    }

    public static RuntimeException shouldNotReachHere(Throwable cause) {
        throw new TornadoInternalError(cause);
    }

    public static void guarantee(boolean condition, String msg, Object... args) {
        if (!condition) {
            throw new TornadoInternalError("failed guarantee: " + msg, args);
        }
    }

    public TornadoInternalError(String msg, Object... args) {
        super(String.format(msg, args));
    }

    public TornadoInternalError(Throwable cause) {
        super(cause);
    }

    public TornadoInternalError addContext(String newContext) {
        context.add(newContext);
        return this;
    }

    public TornadoInternalError addContext(String name, Object obj) {
        return addContext(String.format("%s: %s", name, obj));
    }

}
