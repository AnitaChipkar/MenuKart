package in.menukart.menukart.api;

public interface APIClientCallback<T> {

    void onSuccess(T success);

    void onFailure(Exception e);

}
