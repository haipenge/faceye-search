package com.faceye.test.component.book.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.book.doc.Book;
import com.faceye.component.book.repository.mongo.BookRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Book DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class BookRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private BookRepository bookRepository = null;

	@Before
	public void before() throws Exception {
//		this.bookRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Book entity = new Book();
		this.bookRepository.save(entity);
		Iterable<Book> entities = this.bookRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Book entity = new Book();
		this.bookRepository.save(entity);
        this.bookRepository.deleteById(entity.getId());
        Iterable<Book> entities = this.bookRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Book entity = new Book();
		this.bookRepository.save(entity);
		Book book=this.bookRepository.findById(entity.getId()).get();
		Assert.assertTrue(book!=null);
	}

	
}
