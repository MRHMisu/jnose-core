package br.ufba.jnose.core.testsmelldetector.testsmell.smell.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import br.ufba.jnose.core.testsmelldetector.testsmell.SmellyElement;
import br.ufba.jnose.core.testsmelldetector.testsmell.smell.ExceptionCatchingThrowing;

public class ExceptionCatchingThrowingTest {
	
	public ExceptionCatchingThrowing exceptionTest;
	FileInputStream fileInputStream;
	CompilationUnit compilationUnit;
	SmellyElement smellyElementList;

	@Before
	public void setUp() throws Exception {
		exceptionTest = new ExceptionCatchingThrowing();
		fileInputStream = new FileInputStream(new File("src/main/java/br/ufba/jnose/core/testsmelldetector/testsmell/smell/tests/fixtures/ExceptionFixture.java"));
	}
	
	@Test
	public void should_get_number_of_tests() {
		try{ 
			CompilationUnit compilationUnit = JavaParser.parse(fileInputStream);
			exceptionTest.runAnalysis(compilationUnit,new CompilationUnit(),"ExceptionFixture","");
			ArrayList<SmellyElement> testes = exceptionTest.list();
			
			assertTrue(testes.size() == 2);
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	@Test
	public void should_get_smells() {
		try{ 
			CompilationUnit compilationUnit = JavaParser.parse(fileInputStream);
			exceptionTest.runAnalysis(compilationUnit,new CompilationUnit(),"ExceptionFixture","");
			ArrayList<SmellyElement> testes = exceptionTest.list();
			
			for(SmellyElement t: testes) {
				System.out.println(t.getHasSmell());
				System.out.println(t.getElementName());
				System.out.println(t.getRange());
				System.out.println("");
			}
		
		    assertEquals(testes.get(0).getElementName(),"should_be_expection_one");
		    //19-24
		    //expection_two
		    //29-34
		
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
