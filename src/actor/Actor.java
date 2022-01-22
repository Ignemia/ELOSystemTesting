package actor;

public abstract class Actor {
    Stats stats;
    protected String name;
    protected String id;

    @Override
    public String toString() {
        return "Actor('%s')".formatted(name);
    }
}