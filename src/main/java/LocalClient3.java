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
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.command.CommandFactory;

import com.myspace.loan_demo.Applicant;
import com.myspace.loan_demo.Loan;

public class LocalClient3 {


        public static void main(String[] args) throws Exception{

System.out.println("=========================================-=");

		// Load the knowledge base:
		KieServices ks = KieServices.Factory.get();
                KieContainer kContainer = ks.getKieClasspathContainer();
        	//KieSession kSession = kContainer.newKieSession("loan_demo_client");
        	KieSession kSession = kContainer.newKieSession();

                kSession.insert(getApplicant());
                kSession.insert(getLoan());
                kSession.insert(getLoan(4000));
                kSession.insert(getLoan(4001));
                int numberOfFiredRules  = kSession.fireAllRules();

                // 処理結果のLoanオブジェクト一覧取得
                System.out.println("number of fired rules:" + numberOfFiredRules);


                QueryResults results;
                System.out.println("-------------------------------------------");
                System.out.println("QuryResults(\"All Loan\")");
                System.out.println("-------------------------------------------");
                results  = kSession.getQueryResults( "All Loan");
                results.forEach(row -> printLoan((Loan)row.get("loan")) );

                System.out.println("-------------------------------------------");
                System.out.println("QuryResults(\"Approved Loan\")");
                System.out.println("-------------------------------------------");
                results  = kSession.getQueryResults( "Approved Loan");
                results.forEach(row -> printLoan((Loan)row.get("loan")) );

                kSession.dispose();
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

	private static Loan getLoan(int amount) {
		Loan loan = new Loan();
		loan.setAmount(amount);
		loan.setDuration(10);
		return loan;
	}

        private static void printLoan(Loan l) {
		System.out.println("Loan["+ l.getAmount() + "]");
		System.out.println(" Is approved : " + l.isApproval());
		System.out.println(" Reason : " + l.getReason());
        }

}
