package ua.ms.util.exception.response;

/** interface that can be used to wrap exceptions
 * when handling them and prepare
 * to return some useful info in HTTP  response
 * @param <T> return type of response, T will be converted to json and return to client
 * @param <E> exception that will be handled
 * */
public interface ExceptionResponseWrapper<T, E extends RuntimeException> {
    /**method that receives as argument an exception we need to wrap*/
    ExceptionResponseWrapper<T, E> of(E exception);
    /** @return response body we need to put into HTTP response */
    T getResponse() throws IllegalStateException;
}
