import controller.PatientApplicationController;
import model.PatientModel;
import view.PatientApplicationView;

public class Main {
    public static void main(String[] args) {
        PatientModel applicationModel = new PatientModel();
        PatientApplicationView applicationView = new PatientApplicationView();
        PatientApplicationController applicationController = new PatientApplicationController(applicationModel, applicationView);
    }
}
