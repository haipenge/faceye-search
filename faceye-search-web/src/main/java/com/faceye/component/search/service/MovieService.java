package com.faceye.component.search.service;

import com.faceye.component.search.doc.Movie;
import com.faceye.feature.service.BaseService;

public interface MovieService extends BaseService<Movie, Long> {
	public Movie getMovieByName(String name);

}
/**@generate-service-source@**/
