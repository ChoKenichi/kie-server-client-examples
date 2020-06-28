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

public class LocalClient {

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

                kSession.insert(getApplicant());
                kSession.insert(getLoan());
                kSession.fireAllRules();

                // 処理結果のLoanオブジェクト一覧取得
                System.out.println("All Loan");
                QueryResults results = kSession.getQueryResults( "All Loan");
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

        private static void printLoan(Loan l) {
		System.out.println("Is approved : " + l.isApproval());
		System.out.println("Reason is: " + l.getReason());
        }

}
