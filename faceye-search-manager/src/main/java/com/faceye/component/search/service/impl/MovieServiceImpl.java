package com.faceye.component.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.faceye.component.search.doc.Movie;
import com.faceye.component.search.repository.mongo.MovieRepository;
import com.faceye.component.search.service.MovieService;
import com.faceye.feature.repository.mongo.DynamicSpecifications;
import com.faceye.feature.service.impl.BaseMongoServiceImpl;
import com.faceye.feature.util.Json;
 
import com.faceye.feature.util.http.Http;
import com.querydsl.core.types.Predicate;

@Service
public class MovieServiceImpl extends BaseMongoServiceImpl<Movie, Long, MovieRepository> implements MovieService {
	// 推送地址
	@Value("#{property['push.host']}#{property['push.movie.url']}")
	private String pushUrl = "";

	@Autowired
	public MovieServiceImpl(MovieRepository dao) {
		super(dao);
	}

	@Override
	public Movie getMovieByName(String name) {
		return dao.getMovieByName(name);
	}

	@Override
	public void doPush() {
		Map<String, Object> searchParams = new HashMap();
		searchParams.put("boolean|isPush", Boolean.FALSE);
		Page<Movie> movies = this.getPage(searchParams, 1, 100);
		if (movies != null && CollectionUtils.isNotEmpty(movies.getContent())) {
			for (Movie movie : movies.getContent()) {
				this.push2ProductEnv(movie);
			}
		}
	}

	@Override
	public void push2ProductEnv(Long id) {
		Movie movie = this.get(id);
		this.push2ProductEnv(movie);
	}

	private void push2ProductEnv(Movie movie) {
		if (movie != null && !movie.getIsPush()) {
			String json = Json.toJson(movie);
			String res = Http.getInstance().postJson(pushUrl, "UTF-8", json);
			if (StringUtils.indexOf(res, "true") != -1) {
				movie.setIsPush(true);
				this.save(movie);
			}
		}
	}
	
	@Override
	public Page<Movie> getPage(Map<String, Object> searchParams, int page, int size)   {
		if (page != 0) {
			page = page - 1;
		}
		// SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		// EntityPath<Address> entityPath = resolver.createPath(entityClass);
		// PathBuilder<Address> builder = new PathBuilder<Address>(entityPath.getType(), entityPath.getMetadata());
		// Path path = entityPath.getRoot();
		// List<Predicate> predicates=DynamicSpecifications.buildPredicates(searchParams, entityClass);
		// Predicate predicate=DynamicSpecifications.builder(predicates);
		// NumberPath numberPath = new NumberPath(Number.class, path, "age");
		// predicates.add(numberPath.eq(15));
		Predicate predicate = DynamicSpecifications.builder(searchParams, entityClass);
		if (predicate != null) {
			logger.debug(">>FaceYe -->Query predicate is:" + predicate.toString());
		}
		Sort sort = new Sort(Direction.DESC, "onlineDate");
		Page<Movie> res = null;
		if (size != 0) {
			Pageable pageable = new PageRequest(page, size, sort);
			res = this.dao.findAll(predicate, pageable);
		} else {
			// OrderSpecifier<Comparable> orderPOrderSpecifier=new OrderSpecifier<Comparable>(new Order(), new NumberExpression<Address>("id") {
			// })
			List<Movie> items = (List) this.dao.findAll(predicate);
			res = new PageImpl<Movie>(items);

		}
		return res;
	}

	@Override
	public Movie getMovieByNameAndFrom(String name, String from) {
		return dao.getMovieByNameAndFrom(name, from);
	}
	

}
/**@generate-service-source@**/
