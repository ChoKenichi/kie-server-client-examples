// https://github.com/kiegroup/drools/blob/master/drools-compiler/src/test/java/org/drools/compiler/integrationtests/SeveralKieSessionsTest.java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.command.CommandFactory;

import com.myspace.loan_demo.Applicant;
import com.myspace.loan_demo.Loan;

public class LocalClient2 {

        public static final String GROUP_ID = "com.myspace";
        public static final String ARTIFACT_ID = "loan-demo";
        public static final String VERSION = "1.0.0-SNAPSHOT";

	public static void main(String[] args) throws Exception{


System.out.println("=========================================-=");

		// Load the knowledge base:
		KieServices ks = KieServices.Factory.get();
                // 作成したルールを使用する
		ReleaseId releaseId = ks.newReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);

		KieContainer kContainer = ks.newKieContainer(releaseId);
		KieSession kSession = kContainer.newKieSession();

		/*
		 * Create the list of commands that we want to fire against the rule engine. In this case we insert 2 objects, applicant and loan,
		 * and we trigger a ruleflow (with the StartProcess command).
		 */
		List<Command<?>> commands = new ArrayList<>();

		//The identifiers that we provide in the insert commands can later be used to retrieve the object from the response.
		commands.add(CommandFactory.newInsert(getApplicant(), "applicant"));
		commands.add(CommandFactory.newInsert(getLoan(), "loan"));
		commands.add(CommandFactory.newFireAllRules("numberOfFiredRules"));


		ExecutionResults results = kSession.execute(CommandFactory.newBatchExecution(commands));

		//We can retrieve the objects from the response using the identifiers we specified in the Insert commands.
	        System.out.println("number of fired rules: " + results.getValue("numberOfFiredRules"));
		Applicant resultApplicant = (Applicant) results.getValue("applicant");
		Loan resultLoan = (Loan) results.getValue("loan");

		printLoan(resultLoan);

System.out.println("=========================================-=");

	}

	private static Applicant getApplicant() {
		Applicant applicant = new Applicant();
		applicant.setName("ddoyle");
		applicant.setCreditScore(230);
		return applicant;
	}

	private static Loan getLoan() {
		Loan loan = new Loan();
		loan.setAmount(2500);
		loan.setDuration(10);
		return loan;
	}

        private static void printLoan(Loan l) {
		System.out.println("Loan["+ l.getAmount() + "]");
		System.out.println(" Is approved : " + l.isApproval());
		System.out.println(" Reason : " + l.getReason());
       }
}
