package callback;

public interface CallbackModel {
    void onSuccess(Object id);
    void onError(Object model,String message);
}
