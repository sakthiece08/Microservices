package com.teqmonic.microservices.mortgagerateservice.service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileRatingEnum {

	A("Rating-A"), B("Rating-B"), C("Rating-C");

	String ratingValue;

}
