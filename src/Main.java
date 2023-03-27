import co.edu.uptc.presenter.BillingSystem;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        try {
            BillingSystem billingSystem = new BillingSystem();
            billingSystem.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}