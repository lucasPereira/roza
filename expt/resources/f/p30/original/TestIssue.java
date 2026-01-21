package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import domain.*;
import exceptions.*;

public class TestIssue {
	
	private Issue issue;
	
	@Before
	public void setUp() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, ProjectNotFoundException, IssueClosedException {
		Company company = new Company("Empresa Ltda");
		Project project = company.createProject("Projeto 1");
		Worker worker = company.createWorker("João");
		company.assignWorkerToProject("Projeto 1", "João");
		this.issue = project.createIssue(worker, "Tarefa Teste", IssueType.INTERN);
	}
	
	@Test
	public void testCloseOpenIssue() throws IssueClosedException {
		this.issue.close();
		assertEquals(this.issue.getStatus(), IssueStatus.CLOSED);
		assertNull(this.issue.getOwner());
	}
	
	@Test(expected=IssueClosedException.class)
	public void testCloseClosedIssue() throws IssueClosedException {
		this.issue.close();
		this.issue.close();
	}
}
