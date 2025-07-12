public interface Users {
    boolean add();
    void edit(int id);
    void delete(int id);
    void search(String Name);
    boolean checkExistence();
    void print();
    boolean login();
}
