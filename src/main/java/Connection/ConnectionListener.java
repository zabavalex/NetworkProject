package Connection;

public interface ConnectionListener {
    public void connect(GameMatch gameMatch);
    public void disconnect(GameMatch gameMatch);
    public void exception(GameMatch gameMatch, Exception e);

}
