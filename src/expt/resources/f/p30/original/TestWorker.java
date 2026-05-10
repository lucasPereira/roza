package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain.*;
import exceptions.*;

public class TestWorker {

	private Worker worker;
	private Issue issue;
	private Project project;
	
//	Delegated Setup para atribuir 10 ocorrências a um trabalhador
	private void assignTenIssuesToWorker() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		for(int i = 0 ; i < 10 ; i++) {
			Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
			this.worker.assignIssue(newIssue);
		}
	}
	
	@Before
	public void setUp() {
		this.worker = new Worker("João");
		this.project = new Project("Projeto 1");
		this.issue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
	}
	
	@Test
	public void testAssignIssue() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		this.worker.assignIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 1);
		assertTrue(this.worker.getAssignedIssues().contains(this.issue));
	}

//	Teste que verifica se é possível atribuir 10 ocorrências a um único tralhador
	@Test
	public void testAssignTenIssuesToWorker() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		assignTenIssuesToWorker();
		assertEquals(this.worker.getAssignedIssues().size(), 10);
	}

//	Teste que verifica se uma exceção correspondente é lançada ao tentar atribuir uma 11ª ocorrência a um trabalhador
	@Test(expected=WorkerFullException.class)
	public void testAssignIssueToFullWorker() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		assignTenIssuesToWorker();
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.worker.assignIssue(newIssue);
	}
	
	@Test(expected=DuplicateIssueException.class)
	public void testAssignDuplicateIssue() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		this.worker.assignIssue(this.issue);
		this.worker.assignIssue(this.issue);
	}
	
	@Test(expected=IssueNotFoundException.class)
	public void testUnassignInexistentIssue() throws IssueNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
		this.worker.assignIssue(this.issue);
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.worker.unassignIssue(newIssue);
	}
	
	@Test
	public void testUnassignIssue() throws WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException {
		this.worker.assignIssue(this.issue);
		this.worker.unassignIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 0);
//		assertFalse(this.worker.getAssignedIssues().contains(this.issue));
	}
	
	@Test
	public void testFinishIssue() throws WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException {
		this.worker.assignIssue(this.issue);
		this.worker.finishIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 0);
		assertEquals(this.issue.getStatus(), IssueStatus.CLOSED);
		assertNull(this.issue.getOwner());
	}
	
	@Test(expected=IssueNotFoundException.class)
	public void testFinishInexistentIssue() throws IssueNotFoundException, IssueClosedException {
		this.worker.finishIssue(this.issue);
	}
	
	@Test(expected=IssueClosedException.class)
	public void testFinishClosedIssue() throws IssueNotFoundException, IssueClosedException, WorkerFullException, DuplicateIssueException {
		this.worker.assignIssue(this.issue);
		this.issue.close();
		this.worker.finishIssue(this.issue);
	}
	
//	Adição deste teste para complementar o teste acima (uma ocorrência fechada, além de não poder ser finalizada, também não pode ser atribuída a nenhum trabalhador)
	@Test(expected=IssueClosedException.class)
	public void testAssignClosedIssue() throws WorkerFullException, DuplicateIssueException, IssueClosedException {
		this.issue.close();
		this.worker.assignIssue(this.issue);
	}
}
