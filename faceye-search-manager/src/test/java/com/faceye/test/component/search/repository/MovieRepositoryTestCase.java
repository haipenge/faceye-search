package com.faceye.test.component.search.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.repository.mongo.MovieRepository;
import com.faceye.test.feature.repository.BaseRepositoryTestCase;
/**
 * Movie DAO 测试
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年5月26日
 */
public class MovieRepositoryTestCase extends BaseRepositoryTestCase {
	@Autowired
	private MovieRepository movieRepository = null;

	@Before
	public void before() throws Exception {
		//this.movieRepository.deleteAll();
	}

	@After
	public void after() throws Exception {

	}

	@Test
	public void testSave() throws Exception {
		Movie entity = new Movie();
		this.movieRepository.save(entity);
		Iterable<Movie> entities = this.movieRepository.findAll();
		Assert.assertTrue(entities.iterator().hasNext());
	}

	@Test
	public void testDelete() throws Exception {
		Movie entity = new Movie();
		this.movieRepository.save(entity);
        this.movieRepository.deleteById(entity.getId());
        Iterable<Movie> entities = this.movieRepository.findAll();
		Assert.assertTrue(!entities.iterator().hasNext());
	}

	@Test
	public void testFindOne() throws Exception {
		Movie entity = new Movie();
		this.movieRepository.save(entity);
		Movie movie=this.movieRepository.findById(entity.getId()).get();
		Assert.assertTrue(movie!=null);
	}

	
}
