package app.vcampus.client.tempmodule;

public class TempModule {
    public String getTestData() {
        return "TestData!";
    }

    public static void main(String[] args) {
        TempModule t = new TempModule();
        System.out.println(t.getTestData());
    }
}
